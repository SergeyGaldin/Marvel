plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_characters"
    moduleUsesCompose = true
    moduleUsesNetworkApi = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
    implementation(projects.uiKit)
    implementation(projects.network)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
}