package config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ApplicationConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val versionCatalog = project.the<VersionCatalogsExtension>().named("libs")

        project.afterEvaluate {
            project.plugins.withId("com.android.application") {
                setupApplicationModule(project, versionCatalog)
            }
        }

        setupPluginsIntoApplicationModule(project)
    }

    private fun setupApplicationModule(
        project: Project,
        versionCatalog: VersionCatalog
    ) = with(project.extensions.findByName("android") as BaseAppModuleExtension) {
        namespace = versionCatalog.findVersion("applicationId").get().toString()
        compileSdk = versionCatalog.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            applicationId = versionCatalog.findVersion("applicationId").get().toString()

            minSdk = versionCatalog.findVersion("minSdk").get().toString().toInt()
            targetSdk = versionCatalog.findVersion("targetSdk").get().toString().toInt()

            versionCode = versionCatalog.findVersion("versionCode").get().toString().toInt()
            versionName = versionCatalog.findVersion("versionName").get().toString()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
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

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    private fun setupPluginsIntoApplicationModule(project: Project) {
        project.plugins.apply("com.android.application")
        project.plugins.apply("org.jetbrains.kotlin.android")
        project.plugins.apply("org.jetbrains.kotlin.plugin.compose")
    }
}