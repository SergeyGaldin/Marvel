package com.gateway.marvel.main.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gateway.marvel.core.navigation.MainScreenContent

data class BottomNavigationItem(
    val mainScreenContent: MainScreenContent,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

fun conditionSwitchScreen(
    navController: NavHostController,
    currentRoute: String?,
    selectRoute: String
) {
    if (currentRoute != selectRoute) navController.navigate(selectRoute) { popUpTo(0) }
}

fun NavGraphBuilder.composableWithAnimations(
    route: String,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route = route,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 500)) +
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight / 4 },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) +
                    scaleIn(
                        initialScale = 0.9f,
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 500)) +
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> -fullHeight / 4 },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) +
                    scaleOut(
                        targetScale = 1.1f,
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 500)) +
                    slideInVertically(
                        initialOffsetY = { fullHeight -> -fullHeight / 4 },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) +
                    scaleIn(
                        initialScale = 0.9f,
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 500)) +
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight / 4 },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) +
                    scaleOut(
                        targetScale = 1.1f,
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    )
        },
        content = content
    )
}