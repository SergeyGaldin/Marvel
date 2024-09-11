package com.gateway.marvel.feature_characters

import androidx.lifecycle.ViewModel
import com.gateway.marvel.network.endpoints.MarvelApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val marvelApi: MarvelApi
) : ViewModel() {
    init {
//        fetchCharacters(marvelApi)
    }
}