package com.gateway.marvel.feature_comics

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
import com.gateway.marvel.ui_kit.components.showToastLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicsRoute(
    nameScreen: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    comicsViewModel: ComicsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val comicsScreenState by comicsViewModel.comicsScreenState
    val isRefreshing = comicsScreenState.isRefreshing

    val onRefresh: () -> Unit = {
        coroutineScope.launch {
            comicsViewModel.fetchComic()
        }
    }

    if (comicsScreenState.comics == null) LaunchedEffect(Unit) {
        onRefresh()
    }

    Scaffold(
        topBar = {
            ComicsTopAppBar(
                nameScreen = nameScreen,
                showOnlyFavoritesComics = comicsScreenState.isShowOnlyFavoritesComics,
                changeStateShowOnlyFavoritesComics = comicsViewModel::changeStateShowOnlyFavoritesComics
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
            println("${comicsScreenState.comics}")
//            if (!comicsScreenState.characters.isNullOrEmpty() && !isRefreshing) CharactersContent(
//                characters = comicsScreenState.characters!!,
//                offset = charactersViewModel.offset.value,
//                total = comicsScreenState.total ?: 0,
//                showOnlyFavoritesCharacters = comicsScreenState.isShowOnlyFavoritesCharacters,
//                onNextCharacters = charactersViewModel::nextCharacters,
//                onPreviousCharacters = charactersViewModel::previousCharacters,
//                onAddFavoriteCharacter = charactersViewModel::addFavoriteCharacter,
//                onDeleteFavoriteCharacter = charactersViewModel::deleteFavoriteCharacter
//            ) else if (comicsScreenState.characters.isNullOrEmpty() && !isRefreshing) {
//                if (comicsScreenState.isShowOnlyFavoritesCharacters) DataEmptyLayout(
//                    text = "Нет избранных персонажей"
//                ) else DataEmptyLayout(
//                    text = "Нет данных",
//                    onRefresh = onRefresh
//                )
//            }
        }
    }

    with(comicsScreenState) {
        if (errorMessage != null) {
            if (throwable == null) {
                showToastLong(context, errorMessage)
                comicsViewModel.clearError()
            } else AlertDialogExceptionInfo(
                dialogErrorText = errorMessage,
                dialogExceptionText = throwable?.message,
                onDismiss = { comicsViewModel.clearError() }
            )
        }
    }
}