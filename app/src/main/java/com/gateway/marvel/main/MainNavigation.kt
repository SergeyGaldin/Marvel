package com.gateway.marvel.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gateway.marvel.core.navigation.MainScreenContent
import com.gateway.marvel.feature_characters.CharactersRoute
import com.gateway.marvel.feature_comics.ComicsRoute
import com.gateway.marvel.feature_settings.SettingsRoute
import com.gateway.marvel.main.utils.composableWithAnimations

@Composable
fun MainContentNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composableWithAnimations(MainScreenContent.Characters.route) {
            CharactersRoute(
                nameScreen = MainScreenContent.Characters.nameScreen
            )
        }

        composableWithAnimations(MainScreenContent.Comics.route) {
            ComicsRoute(
                nameScreen = MainScreenContent.Comics.nameScreen
            )
        }

        composableWithAnimations(MainScreenContent.Settings.route) {
            SettingsRoute(
                nameScreen = MainScreenContent.Settings.nameScreen
            )
        }
    }
}