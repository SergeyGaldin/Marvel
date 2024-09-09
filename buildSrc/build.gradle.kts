plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("config.AndroidConfigPlugin") {
            id = "config.AndroidConfigPlugin"
            implementationClass = "config.AndroidConfigPlugin"
        }
    }
}