package config

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class LibraryConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libraryConfig = project
            .extensions
            .create("libraryConfig", LibraryConfigExtension::class.java)

        val versionCatalog = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.afterEvaluate {
            if (project.isLibraryModule()) {
                project.configureLibraryModule(versionCatalog, libraryConfig)
                project.lateConfigurePlugins(libraryConfig)
                project.setupDependencies(versionCatalog, libraryConfig)
            }
        }

        project.configurePlugins()
    }

    private fun Project.isLibraryModule(): Boolean = plugins.hasPlugin("com.android.library")

    private fun Project.configureLibraryModule(
        versionCatalog: VersionCatalog,
        libraryConfig: LibraryConfigExtension
    ) = with(extensions.getByName<LibraryExtension>("android")) {
        namespace = "${versionCatalog.findVersion("applicationId").get()}" +
                ".${libraryConfig.namespace}"
        compileSdk = versionCatalog.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            minSdk = versionCatalog.findVersion("minSdk").get().toString().toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        tasks.withType(KotlinCompile::class.java).configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }

        buildFeatures {
            buildConfig = true
        }
    }

    private fun Project.configurePlugins() = with(plugins) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
    }

    private fun Project.lateConfigurePlugins(
        libraryConfig: LibraryConfigExtension
    ) = with(plugins) {
        if (libraryConfig.moduleUsesCompose) {
            apply("org.jetbrains.kotlin.plugin.compose")
        }
    }

    private fun Project.setupDependencies(
        versionCatalog: VersionCatalog,
        libraryConfig: LibraryConfigExtension
    ) = dependencies.apply {
        addDependency(versionCatalog, "androidx.core.ktx")
        addDependency(versionCatalog, "androidx.lifecycle.runtime.ktx")

        if (libraryConfig.moduleUsesCompose) {
            addDependency(versionCatalog, "androidx.activity.compose")
            addDependency(versionCatalog, "androidx.compose.material.iconsExtended")
            addDependency(versionCatalog, "androidx.material3")

            addPlatformDependency(versionCatalog, "androidx.compose.bom")
            addDependency(versionCatalog, "androidx.runtime")
            addDependency(versionCatalog, "androidx.ui")
            addDependency(versionCatalog, "androidx.ui.graphics")
            addDependency(versionCatalog, "androidx.ui.tooling.preview")
            addDependencyDebug(versionCatalog, "androidx.ui.tooling")
        }
    }

    private fun DependencyHandler.addDependency(
        versionCatalog: VersionCatalog,
        alias: String
    ) = add("implementation", versionCatalog.findLibrary(alias).get())

    private fun DependencyHandler.addPlatformDependency(
        versionCatalog: VersionCatalog,
        alias: String
    ) = add("implementation", platform(versionCatalog.findLibrary(alias).get()))

    private fun DependencyHandler.addDependencyDebug(
        versionCatalog: VersionCatalog,
        alias: String
    ) = add("debugImplementation", versionCatalog.findLibrary(alias).get())
}

open class LibraryConfigExtension {
    var namespace: String = ""
    var moduleUsesCompose: Boolean = false
}