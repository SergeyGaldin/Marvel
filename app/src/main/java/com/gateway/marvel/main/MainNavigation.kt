package com.gateway.marvel.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gateway.marvel.feature_characters.CharactersRoute
import com.gateway.marvel.feature_comics.ComicsScreen
import com.gateway.marvel.feature_settings.SettingsRoute

object DestinationMainContent {
    const val CHARACTERS_ROUTE = "characters"
    const val COMICS_ROUTE = "comics"
    const val SETTINGS_ROUTE = "settings"
}

sealed class MainScreenContent(
    val route: String,
    val nameScreen: String
) {
    data object Characters : MainScreenContent(
        route = DestinationMainContent.CHARACTERS_ROUTE,
        nameScreen = "Персонажи"
    )

    data object Comics : MainScreenContent(
        route = DestinationMainContent.COMICS_ROUTE,
        nameScreen = "Комиксы"
    )

    data object Settings : MainScreenContent(
        route = DestinationMainContent.SETTINGS_ROUTE,
        nameScreen = "Настройки"
    )
}

@Composable
fun MainContentNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainScreenContent.Characters.route) {
            CharactersRoute(
                nameScreen = MainScreenContent.Characters.nameScreen
            )
        }

        composable(MainScreenContent.Comics.route) {
            ComicsScreen()
        }

        composable(MainScreenContent.Settings.route) {
            SettingsRoute()
        }
    }
}

fun conditionSwitchScreen(
    navController: NavHostController,
    currentRoute: String?,
    selectRoute: String
) {
    if (currentRoute != selectRoute) navController.navigate(selectRoute) { popUpTo(0) }
}