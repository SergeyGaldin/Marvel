plugins {
    id("config.LibraryConfigPlugin")

    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

libraryConfig {
    namespace = "feature_characters"
    moduleUsesCompose = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}