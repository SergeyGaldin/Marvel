package com.gateway.marvel.feature_characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.gateway.marvel.ui_kit.components.AlertDialogExceptionInfo
import com.gateway.marvel.ui_kit.components.DataEmptyLayout
import com.gateway.marvel.ui_kit.components.showToastLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersRoute(
    nameScreen: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    charactersViewModel: CharactersViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val charactersScreenState by charactersViewModel.charactersScreenState
    val isRefreshing = charactersScreenState.isRefreshing

    val onRefresh: () -> Unit = {
        coroutineScope.launch {
            charactersViewModel.fetchCharacters()
        }
    }

    if (charactersScreenState.characters == null) LaunchedEffect(Unit) {
        onRefresh()
    }

    Scaffold(
        topBar = {
            CharactersTopAppBar(
                nameScreen = nameScreen,
                showOnlyFavoritesCharacters = charactersScreenState.isShowOnlyFavoritesCharacters,
                changeStateShowOnlyFavoritesCharacters = charactersViewModel::changeStateShowOnlyFavoritesCharacters
            )
        }
    ) {
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            if (!charactersScreenState.characters.isNullOrEmpty() && !isRefreshing) CharactersContent(
                characters = charactersScreenState.characters!!,
                offset = charactersViewModel.offset.value,
                limit = charactersViewModel.limit.value,
                total = charactersScreenState.total ?: 0,
                showOnlyFavoritesCharacters = charactersScreenState.isShowOnlyFavoritesCharacters,
                onNextCharacters = charactersViewModel::nextCharacters,
                onPreviousCharacters = charactersViewModel::previousCharacters,
                onAddFavoriteCharacter = charactersViewModel::addFavoriteCharacter,
                onDeleteFavoriteCharacter = charactersViewModel::deleteFavoriteCharacter
            ) else if (charactersScreenState.characters.isNullOrEmpty() && !isRefreshing) {
                if (charactersScreenState.isShowOnlyFavoritesCharacters) DataEmptyLayout(
                    text = "Нет избранных персонажей"
                ) else DataEmptyLayout(
                    text = "Нет данных",
                    onRefresh = onRefresh
                )
            }
        }
    }

    with(charactersScreenState) {
        if (errorMessage != null) {
            if (throwable == null) {
                showToastLong(context, errorMessage)
                charactersViewModel.clearError()
            } else AlertDialogExceptionInfo(
                dialogErrorText = errorMessage,
                dialogExceptionText = throwable?.message,
                onDismiss = { charactersViewModel.clearError() }
            )
        }
    }
}