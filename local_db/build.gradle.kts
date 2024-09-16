plugins {
    id("config.LibraryConfigPlugin")
}

libraryConfig {
    namespace = "local_db"
    moduleUsesLocalDB = true
    moduleUsesKSP = true
    moduleUsesHilt = true
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(projects.core)

    implementation(libs.gson)
}