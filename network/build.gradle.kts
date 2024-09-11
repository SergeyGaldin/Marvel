plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "network"
    moduleUsesNetworkApi = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
}