package com.gateway.marvel.core.navigation

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