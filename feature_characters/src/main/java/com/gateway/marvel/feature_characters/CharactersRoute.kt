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
import com.gateway.marvel.ui_kit.components.showToastLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersRoute(
    charactersViewModel: CharactersViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
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
        topBar = {}
    ) {
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            CharactersScreen(charactersScreenState.characters)
        }
    }

    with(charactersScreenState) {
        if (errorMessage != null) {
            if (throwable == null) {
                showToastLong(context, errorMessage)
                clearError()
            } else AlertDialogExceptionInfo(
                dialogErrorText = errorMessage,
                dialogExceptionText = throwable?.message,
                onDismiss = { clearError() }
            )
        }
    }
}