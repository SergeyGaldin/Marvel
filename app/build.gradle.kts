plugins {
    id("config.ApplicationConfigPlugin")

    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(projects.core)
    implementation(projects.uiKit)
    implementation(projects.network)
    implementation(projects.localDb)
    implementation(projects.featureCharacters)
    implementation(projects.featureComics)
    implementation(projects.featureSettings)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.coil.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.retrofit) { exclude(module = "okhttp") }
    implementation(libs.retrofit2.adapter.rxjava3)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.google.accompanist.systemuicontroller)
    implementation(libs.gson)

    implementation(libs.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.dagger.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}