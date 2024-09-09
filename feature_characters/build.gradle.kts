plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_characters"
    moduleUsesCompose = true
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}