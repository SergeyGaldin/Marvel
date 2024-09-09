plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_characters"
    moduleUsesCompose = true
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}