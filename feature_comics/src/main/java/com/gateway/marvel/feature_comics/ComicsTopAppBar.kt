package com.gateway.marvel.feature_comics

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.gateway.marvel.ui_kit.components.CenterAlignedTopAppBarBase

@Composable
fun ComicsTopAppBar(
    nameScreen: String,
    showOnlyFavoritesComics: Boolean,
    changeStateShowOnlyFavoritesComics: () -> Unit
) {
    val iconFavorite = if (showOnlyFavoritesComics) Icons.Default.Star
    else Icons.Default.StarOutline

    CenterAlignedTopAppBarBase(
        title = nameScreen,
        actions = {
            IconButton(
                onClick = changeStateShowOnlyFavoritesComics
            ) {
                Icon(
                    imageVector = iconFavorite,
                    contentDescription = null
                )
            }
        }
    )
}