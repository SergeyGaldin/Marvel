plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "local_preferences"
    moduleUsesHilt = true
}

dependencies {
    implementation(projects.core)
}