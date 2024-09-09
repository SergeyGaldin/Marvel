package config

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class LibraryConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.withId("com.android.library") {
            val versionCatalog = project.the<VersionCatalogsExtension>().named("libs")
            val androidExtension = project.extensions.findByName("android") as LibraryExtension

            androidExtension.apply {
                namespace = "com.gateway.marvel.feature_characters"
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
                    kotlinOptions {
                        jvmTarget = "17"
                    }
                }
//                buildFeatures {
//                    compose = true
//                    buildConfig = true
//                }
//                composeOptions {
//                    kotlinCompilerExtensionVersion = versionCatalog.findVersion("kotlinCompiler").get().toString()
//                }
            }
        }
    }
}