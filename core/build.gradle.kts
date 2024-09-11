plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "core"
    moduleUsesCompose = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}