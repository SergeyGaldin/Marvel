plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_comics"
    moduleUsesCompose = true
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}