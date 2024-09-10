plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.gradle.plugin)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("config.ApplicationConfigPlugin") {
            id = "config.ApplicationConfigPlugin"
            implementationClass = "config.ApplicationConfigPlugin"
        }

        register("config.LibraryConfigPlugin") {
            id = "config.LibraryConfigPlugin"
            implementationClass = "config.LibraryConfigPlugin"
        }
    }
}