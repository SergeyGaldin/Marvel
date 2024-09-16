plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "repo_characters"
    moduleUsesNetworkApi = true
    moduleUsesLocalDB = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
    implementation(projects.network)
    implementation(projects.localDb)
}