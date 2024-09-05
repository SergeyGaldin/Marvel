package com.gateway.marvel.ui.main

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gateway.marvel.ui.theme.MarvelTheme

@Preview(name = "Main light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Main in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenPreview() {
    MarvelTheme {
        MainScreen()
    }
}

data class BottomNavigationItem(
    val mainScreenContent: MainScreenContent,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        mainScreenContent = MainScreenContent.Movies,
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie
    ),
    BottomNavigationItem(
        mainScreenContent = MainScreenContent.Characters,
        selectedIcon = Icons.Filled.SupervisedUserCircle,
        unselectedIcon = Icons.Outlined.SupervisedUserCircle
    ),
    BottomNavigationItem(
        mainScreenContent = MainScreenContent.Comics,
        selectedIcon = Icons.Filled.Book,
        unselectedIcon = Icons.Outlined.Book
    ),
    BottomNavigationItem(
        mainScreenContent = MainScreenContent.Settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current

    BackHandler {
        (context as ComponentActivity).finishAffinity()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        content = { contentPadding ->
            MainContent(
                contentPadding = contentPadding,
                navController = navController
            )
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        }
    )
}

@Composable
private fun MainContent(
    contentPadding: PaddingValues,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        MainContentNavGraph(
            navController = navController,
            startDestination = MainScreenContent.Movies.route
        )
    }
}

@Composable
private fun BottomNavBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.mainScreenContent.route,
                onClick = {
                    conditionSwitchScreen(
                        navController,
                        currentDestination?.route,
                        item.mainScreenContent.route
                    )
                },
                label = {
                    Text(
                        text = item.mainScreenContent.title,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.route == item.mainScreenContent.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.mainScreenContent.title
                    )
                }
            )
        }
    }
}