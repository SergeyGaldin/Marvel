package com.gateway.marvel.feature_characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.ui_kit.components.DataEmptyLayout

@Composable
fun CharactersScreen(
    characters: List<Character>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
       items(characters) { character ->
            ListItem(
                headlineContent = {
                    Text(text = character.name)
                }
            )
        }
    }
}