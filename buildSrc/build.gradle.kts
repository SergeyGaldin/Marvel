plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.5.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.52")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.25-1.0.20")
}

gradlePlugin {
    plugins {
        register("config.AndroidConfigPlugin") {
            id = "config.AndroidConfigPlugin"
            implementationClass = "config.AndroidConfigPlugin"
        }
    }
}