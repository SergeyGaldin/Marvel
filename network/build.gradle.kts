plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "network"
    moduleUsesNetworkApi = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
}