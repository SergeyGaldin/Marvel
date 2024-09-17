package com.gateway.marvel.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gateway.marvel.core.navigation.MainScreenContent
import com.gateway.marvel.feature_characters.CharactersRoute
import com.gateway.marvel.feature_comics.ComicsScreen
import com.gateway.marvel.feature_settings.SettingsRoute

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
            SettingsRoute(
                nameScreen = MainScreenContent.Settings.nameScreen
            )
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