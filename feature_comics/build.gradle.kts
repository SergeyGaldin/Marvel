plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_comics"
    moduleUsesCompose = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
    implementation(projects.uiKit)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
}