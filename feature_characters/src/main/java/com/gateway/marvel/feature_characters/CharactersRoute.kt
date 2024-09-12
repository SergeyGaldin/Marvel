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
import com.gateway.marvel.ui_kit.components.CenterAlignedTopAppBarBase
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
            CenterAlignedTopAppBarBase(
                title = nameScreen
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
            if (!charactersScreenState.characters.isNullOrEmpty()) CharactersContent(
                characters = charactersScreenState.characters!!
            ) else if (charactersScreenState.characters.isNullOrEmpty() && !isRefreshing) DataEmptyLayout(
                onRefresh = onRefresh
            )
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