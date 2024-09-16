package com.gateway.marvel.feature_characters

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.mock.MockEntity
import com.gateway.marvel.core.utils.CommonConstants
import com.gateway.marvel.ui_kit.theme.MarvelTheme

@Composable
fun CharactersContent(
    characters: List<Character>,
    offset: Int,
    total: Int,
    onNextCharacters: () -> Unit,
    onPreviousCharacters: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(characters) { character ->
                CharacterItem(character = character)
            }
        }

        PaginationLayout(
            offset = offset,
            total = total,
            onNextCharacters = onNextCharacters,
            onPreviousCharacters = onPreviousCharacters
        )
    }
}

@Composable
private fun PaginationLayout(
    offset: Int,
    total: Int,
    onNextCharacters: () -> Unit,
    onPreviousCharacters: () -> Unit,
) {
    val offsetFinal = offset + CommonConstants.LIMIT_CHARACTER

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = onPreviousCharacters
        ) {
            Text(text = "Предыдущие")
        }

        Text(text = "$offset .. $offsetFinal / $total")

        TextButton(
            onClick = onNextCharacters
        ) {
            Text(text = "Следующие")
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character
) {
    var savedToFavorites by remember { mutableStateOf(false) }
    val iconFavorite = if (savedToFavorites) Icons.Default.Star else Icons.Default.StarOutline

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(
                    MaterialTheme.shapes.medium.copy(
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                ),
            model = "${character.thumbnail.path}.${character.thumbnail.extension}",
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        HorizontalDivider(
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.errorContainer
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .padding(horizontal = 3.dp),
            text = character.name.uppercase(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                savedToFavorites = !savedToFavorites
                // TODO: Сохранение в избранное
            }
        ) {
            Icon(
                imageVector = iconFavorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
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

@Preview(showBackground = true)
@Composable
private fun PaginationLayoutPreview() {
    MarvelTheme {
        PaginationLayout(
            offset = 0,
            total = 1574,
            onNextCharacters = {},
            onPreviousCharacters = {}
        )
    }
}