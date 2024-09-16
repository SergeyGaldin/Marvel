rootProject.name = "Marvel"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(":app")

include(":ui_kit")

include(":core")
include(":network")
include(":local_db")

include(":feature_characters")
include(":feature_comics")
include(":feature_settings")

include(":repo_characters")
