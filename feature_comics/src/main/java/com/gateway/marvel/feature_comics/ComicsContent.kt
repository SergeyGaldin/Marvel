package com.gateway.marvel.feature_comics

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.core.mock.MockEntity
import com.gateway.marvel.ui_kit.theme.MarvelTheme

@Composable
fun ComicsContent(
    comics: List<Comic>,
    offset: Int,
    limit: Int,
    total: Int,
    showOnlyFavoritesComics: Boolean,
    onNextComics: () -> Unit,
    onPreviousComics: () -> Unit,
    onAddFavoriteComic: (Comic) -> Unit,
    onDeleteFavoriteComic: (Comic) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(comics) { comic ->
                ComicItem(
                    comic = comic,
                    isFavorite = comic.isFavorite,
                    onAddFavoriteComic = onAddFavoriteComic,
                    onDeleteFavoriteComic = onDeleteFavoriteComic
                )
            }
        }

        if (showOnlyFavoritesComics) LocalInfoLayout(
            total = total
        ) else PaginationLayout(
            offset = offset,
            limit = limit,
            total = total,
            onNextComics = onNextComics,
            onPreviousComics = onPreviousComics
        )
    }
}

@Composable
private fun LocalInfoLayout(
    total: Int
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$total избранных комиксов"
        )
    }
}

@Composable
private fun PaginationLayout(
    offset: Int,
    limit: Int,
    total: Int,
    onNextComics: () -> Unit,
    onPreviousComics: () -> Unit,
) {
    val offsetFinal = offset + limit

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = onPreviousComics
        ) {
            Text(text = "Предыдущие")
        }

        Text(text = "$offset .. $offsetFinal / $total")

        TextButton(
            onClick = onNextComics
        ) {
            Text(text = "Следующие")
        }
    }
}

@Composable
private fun ComicItem(
    comic: Comic,
    isFavorite: Boolean,
    onAddFavoriteComic: (Comic) -> Unit,
    onDeleteFavoriteComic: (Comic) -> Unit,
) {
    val savedToFavorites by rememberUpdatedState(isFavorite)
    val iconFavorite = if (savedToFavorites) Icons.Default.Star else Icons.Default.StarOutline

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(
                    MaterialTheme.shapes.medium.copy(
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                ),
            model = "${comic.thumbnail.path}.${comic.thumbnail.extension}",
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
            text = comic.title.uppercase(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val newFavoriteState = !savedToFavorites
                if (newFavoriteState) onAddFavoriteComic(comic)
                else onDeleteFavoriteComic(comic)
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
        ComicItem(
            comic = MockEntity.mockComic(),
            isFavorite = true,
            onAddFavoriteComic = {},
            onDeleteFavoriteComic = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PaginationLayoutPreview() {
    MarvelTheme {
        PaginationLayout(
            offset = 0,
            limit = 20,
            total = 1574,
            onNextComics = {},
            onPreviousComics = {}
        )
    }
}