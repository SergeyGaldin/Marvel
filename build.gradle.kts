buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.compose.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.com.google.devtools.ksp.gradle.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}