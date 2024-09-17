package com.gateway.marvel.feature_settings

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsRoute(
    nameScreen: String,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                nameScreen = nameScreen
            )
        }
    ) {
        SettingsContent(
            contentPadding = it,
            settingsViewModel = settingsViewModel
        )
    }
}