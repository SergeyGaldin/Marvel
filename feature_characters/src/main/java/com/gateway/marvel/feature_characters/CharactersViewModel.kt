package com.gateway.marvel.feature_characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.utils.CommonConstants
import com.gateway.marvel.repo_characters.CharactersRepository
import com.gateway.marvel.ui_kit.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharactersScreenState(
    val characters: List<Character>? = null,
    val total: Int? = null,
    val isShowOnlyFavoritesCharacters: Boolean = false,
    override val isRefreshing: Boolean = true,
    override var errorMessage: String? = null,
    override var throwable: Throwable? = null
) : ScreenState()

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {
    private val _charactersScreenState = mutableStateOf(CharactersScreenState())
    val charactersScreenState: State<CharactersScreenState> get() = _charactersScreenState

    private val _offset = mutableIntStateOf(CommonConstants.START_OFFSET_CHARACTER)
    val offset: State<Int> get() = _offset

    suspend fun fetchCharacters() {
        _charactersScreenState.value = _charactersScreenState.value.copy(isRefreshing = true)

        val charactersData = charactersRepository.fetchCharacters(
            isGetLocalData = _charactersScreenState.value.isShowOnlyFavoritesCharacters,
            offset = _offset.intValue
        )

        _charactersScreenState.value = _charactersScreenState.value.copy(
            characters = charactersData.characters,
            total = charactersData.total,
            isRefreshing = false,
            errorMessage = charactersData.errorMessage,
            throwable = charactersData.throwable
        )
    }

    fun addFavoriteCharacter(character: Character) {
        _charactersScreenState.value = _charactersScreenState.value.copy(
            characters = _charactersScreenState.value.characters?.map {
                if (it.id == character.id) it.copy(isFavorite = true) else it
            }
        )

        viewModelScope.launch {
            charactersRepository.addFavoriteCharacter(character)
        }
    }

    fun deleteFavoriteCharacter(character: Character) {
        _charactersScreenState.value = _charactersScreenState.value.copy(
            characters = _charactersScreenState.value.characters?.map {
                if (it.id == character.id) it.copy(isFavorite = false) else it
            }
        )

        viewModelScope.launch {
            charactersRepository.deleteFavoriteCharacter(character)
        }
    }

    fun nextCharacters() {
        _offset.intValue += CommonConstants.LIMIT_CHARACTER
        getCharacters()
    }

    fun previousCharacters() {
        _offset.intValue -= CommonConstants.LIMIT_CHARACTER
        getCharacters()
    }

    fun clearError() {
        _charactersScreenState.value = _charactersScreenState.value.copy(
            errorMessage = null,
            throwable = null
        )
    }

    fun changeStateShowOnlyFavoritesCharacters() {
        _charactersScreenState.value = _charactersScreenState.value.copy(
            isShowOnlyFavoritesCharacters = !_charactersScreenState.value.isShowOnlyFavoritesCharacters
        )
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            fetchCharacters()
        }
    }
}