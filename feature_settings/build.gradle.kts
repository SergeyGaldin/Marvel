plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_settings"
    moduleUsesCompose = true
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}