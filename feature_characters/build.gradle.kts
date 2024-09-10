plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "feature_characters"
    moduleUsesCompose = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}