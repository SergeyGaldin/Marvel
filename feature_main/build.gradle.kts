plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_main"
    moduleUsesCompose = true
}

dependencies {
    implementation(projects.uiKit)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}