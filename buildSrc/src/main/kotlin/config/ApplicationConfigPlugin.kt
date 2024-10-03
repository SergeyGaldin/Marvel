package config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ApplicationConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val versionCatalog = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.afterEvaluate {
            if (project.isApplicationModule()) {
                project.configureApplicationModule(versionCatalog)
            }
        }

        project.configurePlugins()
    }

    private fun Project.isApplicationModule(): Boolean =
        plugins.hasPlugin("com.android.application")

    private fun Project.configureApplicationModule(
        versionCatalog: VersionCatalog
    ) = with(extensions.getByName<BaseAppModuleExtension>("android")) {
        namespace = versionCatalog.findVersion("applicationId").get().toString()
        compileSdk = versionCatalog.findVersion("compileSdk").get().toString().toInt()

        signingConfigs {
            create("marvel release") {
                storeFile = file("marvel_key.jks")
                storePassword = "rootroot"
                keyAlias = "marvel_key"
                keyPassword = "rootroot"
            }
        }

        defaultConfig {
            applicationId = versionCatalog.findVersion("applicationId").get().toString()

            minSdk = versionCatalog.findVersion("minSdk").get().toString().toInt()
            targetSdk = versionCatalog.findVersion("targetSdk").get().toString().toInt()

            versionCode = versionCatalog.findVersion("versionCode").get().toString().toInt()
            versionName = versionCatalog.findVersion("versionName").get().toString()

            testInstrumentationRunner = "com.gateway.marvel.CustomTestRunner"
            vectorDrawables.useSupportLibrary = true

            resourceConfigurations += listOf("ru")
        }

        buildTypes {
            getByName("release") {
                signingConfig = signingConfigs.getByName("marvel release")
                isMinifyEnabled = true
                isShrinkResources = true
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

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    private fun Project.configurePlugins() = with(plugins) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
        apply("org.jetbrains.kotlin.plugin.compose")
    }
}
