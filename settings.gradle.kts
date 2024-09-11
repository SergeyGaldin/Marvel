rootProject.name = "Marvel"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(":app")
include(":core")
include(":ui_kit")

include(":feature_main")
include(":feature_characters")
include(":feature_comics")
include(":feature_settings")
