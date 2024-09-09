plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "ui_kit"
    moduleUsesCompose = true
}

dependencies {
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.google.accompanist.systemuicontroller)
}