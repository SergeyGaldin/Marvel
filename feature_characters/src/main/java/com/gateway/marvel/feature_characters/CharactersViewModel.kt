package com.gateway.marvel.feature_characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.marvel.repo_characters.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {
    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            charactersRepository.fetchCharacters()
        }
    }
}