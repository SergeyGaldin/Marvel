package com.gateway.marvel.feature_settings

import androidx.compose.runtime.Composable
import com.gateway.marvel.ui_kit.components.CenterAlignedTopAppBarBase

@Composable
fun SettingsTopAppBar(
    nameScreen: String
) {
    CenterAlignedTopAppBarBase(
        title = nameScreen
    )
}