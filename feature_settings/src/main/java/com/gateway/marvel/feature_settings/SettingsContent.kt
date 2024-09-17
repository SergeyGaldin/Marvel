package com.gateway.marvel.feature_settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gateway.marvel.core.navigation.MainScreenContent
import com.gateway.marvel.ui_kit.components.RadioButtonGroup

private val mainScreenContents = listOf(
    MainScreenContent.Characters,
    MainScreenContent.Comics
)

@Composable
fun SettingsContent(
    contentPadding: PaddingValues,
    settingsViewModel: SettingsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        StartDestinationContent(settingsViewModel = settingsViewModel)
    }
}

private fun checkScreen(startDestination: String) = when (startDestination) {
    MainScreenContent.Characters.route -> MainScreenContent.Characters
    MainScreenContent.Comics.route -> MainScreenContent.Comics
    else -> MainScreenContent.Characters
}

@Composable
private fun StartDestinationContent(
    settingsViewModel: SettingsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(
                vertical = 16.dp,
                horizontal = 10.dp
            )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Стартовый экран",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        RadioButtonGroup(
            list = mainScreenContents,
            textItem = { it.nameScreen },
            selectedItem = checkScreen(settingsViewModel.startDestination),
            onSelect = { settingsViewModel.startDestination = it.route }
        )
    }
}