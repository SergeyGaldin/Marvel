package com.gateway.marvel.feature_characters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.gateway.marvel.ui_kit.components.CenterAlignedTopAppBarBase

@Composable
fun CharactersTopAppBar(
    nameScreen: String,
    showOnlyFavoritesCharacters: MutableState<Boolean>
) {
    val iconFavorite = if (showOnlyFavoritesCharacters.value) Icons.Default.Star
    else Icons.Default.StarOutline

    CenterAlignedTopAppBarBase(
        title = nameScreen,
        actions = {
            IconButton(
                onClick = {
                    showOnlyFavoritesCharacters.value = !showOnlyFavoritesCharacters.value
                }
            ) {
                Icon(
                    imageVector = iconFavorite,
                    contentDescription = null
                )
            }
        }
    )
}