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
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
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
        apply("com.google.devtools.ksp")
    }

    private fun Project.lateConfigurePlugins(
        libraryConfig: LibraryConfigExtension
    ) = with(plugins) {
        if (libraryConfig.moduleUsesCompose) {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        if (libraryConfig.moduleUsesHilt) {
            apply("dagger.hilt.android.plugin")
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
            addDependency(versionCatalog, "coil.compose")
            addDependency(versionCatalog, "androidx.compose.material.iconsExtended")

            addPlatformDependency(versionCatalog, "androidx.compose.bom")
            addDependency(versionCatalog, "androidx.runtime")
            addDependency(versionCatalog, "androidx.material3")
            addDependency(versionCatalog, "androidx.ui")
            addDependency(versionCatalog, "androidx.ui.graphics")
            addDependency(versionCatalog, "androidx.ui.tooling.preview")
            addDependencyDebug(versionCatalog, "androidx.ui.tooling")
        }

        if (libraryConfig.moduleUsesNetworkApi) {
            addDependency(versionCatalog, "retrofit")
            addDependency(versionCatalog, "retrofit2.adapter.rxjava3")
            addDependency(versionCatalog, "retrofit2.converter.gson")
            addDependency(versionCatalog, "okhttp")
            addDependency(versionCatalog, "logging.interceptor")
        }

        if (libraryConfig.moduleUsesHilt) {
            addDependency(versionCatalog, "hilt.android")
            addDependency(versionCatalog, "hilt.navigation.compose")
            addKSPDependency(versionCatalog, "dagger.hilt.android.compiler")
        }

        if (libraryConfig.moduleUsesLocalDB) {
            addDependency(versionCatalog, "room.runtime")
            addDependency(versionCatalog, "room.ktx")
            addKSPDependency(versionCatalog, "room.compiler")
        }

        if (libraryConfig.moduleUsesPreferences) {
            addDependency(versionCatalog, "preference.ktx")
        }
    }

    private fun DependencyHandler.addDependency(
        versionCatalog: VersionCatalog,
        alias: String
    ) = add("implementation", versionCatalog.findLibrary(alias).get())

    private fun DependencyHandler.addKSPDependency(
        versionCatalog: VersionCatalog,
        alias: String
    ) = add("ksp", versionCatalog.findLibrary(alias).get())

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
    var moduleUsesHilt: Boolean = false
    var moduleUsesNetworkApi: Boolean = false
    var moduleUsesLocalDB: Boolean = false
    var moduleUsesPreferences: Boolean = false
}