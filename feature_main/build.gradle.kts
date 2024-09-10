plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_main"
    moduleUsesCompose = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.uiKit)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}