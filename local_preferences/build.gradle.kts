plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "local_preferences"
    moduleUsesHilt = true
    moduleUsesPreferences = true
}

dependencies {
    implementation(projects.core)
}