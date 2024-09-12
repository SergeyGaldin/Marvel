plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "repo_characters"
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
    implementation(projects.network)
}