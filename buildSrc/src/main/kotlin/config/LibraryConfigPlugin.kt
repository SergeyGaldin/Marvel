package config

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class LibraryConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libraryConfig = project
            .extensions
            .create("libraryConfig", LibraryConfigExtension::class.java)

        val versionCatalog = project.the<VersionCatalogsExtension>().named("libs")

        project.afterEvaluate {
            project.plugins.withId("com.android.library") {
                setupLibraryModule(project, versionCatalog, libraryConfig)
            }

            setupDependenciesIntoLibraryModule(project, versionCatalog, libraryConfig)
        }

        setupPluginsIntoLibraryModule(project)
    }

    private fun setupLibraryModule(
        project: Project,
        versionCatalog: VersionCatalog,
        libraryConfig: LibraryConfigExtension
    ) = with(project.extensions.findByName("android") as LibraryExtension) {
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

        project.tasks.withType(KotlinCompile::class.java).configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }

        buildFeatures {
            buildConfig = true
        }
    }

    private fun setupPluginsIntoLibraryModule(project: Project) {
        project.plugins.apply("com.android.library")
        project.plugins.apply("org.jetbrains.kotlin.android")
        project.plugins.apply("org.jetbrains.kotlin.plugin.compose")
    }

    private fun setupDependenciesIntoLibraryModule(
        project: Project,
        versionCatalog: VersionCatalog,
        libraryConfig: LibraryConfigExtension
    ) {
        project.dependencies.apply {
            add("implementation", versionCatalog.findLibrary("androidx.core.ktx").get())
            add("implementation", versionCatalog.findLibrary("androidx.lifecycle.runtime.ktx").get())
            if(libraryConfig.moduleUsesCompose) {
                add("implementation", versionCatalog.findLibrary("androidx.activity.compose").get())
                add("implementation", versionCatalog.findLibrary("androidx.compose.material.iconsExtended").get())
                add("implementation", platform(versionCatalog.findLibrary("androidx.compose.bom").get()))
                add("implementation", versionCatalog.findLibrary("androidx.runtime").get())
                add("implementation", versionCatalog.findLibrary("androidx.ui").get())
                add("implementation", versionCatalog.findLibrary("androidx.ui.graphics").get())
                add("implementation", versionCatalog.findLibrary("androidx.ui.tooling.preview").get())
                add("implementation", versionCatalog.findLibrary("androidx.material3").get())
                add("debugImplementation", versionCatalog.findLibrary("androidx.ui.tooling").get())
            }
        }
    }
}

open class LibraryConfigExtension {
    var namespace: String = ""
    var moduleUsesCompose: Boolean = false
}