plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "local_db"
    moduleUsesLocalDB = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
}