rootProject.name = "Marvel"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(":app")
include(":ui_kit")

include(":feature_main")
include(":feature_characters")
include(":feature_comics")
include(":feature_settings")