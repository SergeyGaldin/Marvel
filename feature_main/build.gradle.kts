plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_main"
    moduleUsesCompose = true
}

dependencies {
    implementation(project(":ui_kit"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}