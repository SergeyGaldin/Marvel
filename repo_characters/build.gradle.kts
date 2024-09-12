plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "repo_characters"
    moduleUsesNetworkApi = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
    implementation(projects.network)
}