package com.gateway.marvel.feature_characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.MockEntity
import com.gateway.marvel.ui_kit.theme.MarvelTheme

@Composable
fun CharactersContent(
    characters: List<Character>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(characters) { character ->
            CharacterItem(character = character)
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character
) {
    ListItem(
        headlineContent = {
            Text(text = character.name)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CharacterItemPreview() {
    MarvelTheme {
        CharacterItem(
            character = MockEntity.mockCharacter()
        )
    }
}