package com.gateway.marvel.feature_characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.marvel.network.endpoints.MarvelApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val marvelApi: MarvelApi
) : ViewModel() {
    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            fetchCharacters(marvelApi)
        }
    }
}