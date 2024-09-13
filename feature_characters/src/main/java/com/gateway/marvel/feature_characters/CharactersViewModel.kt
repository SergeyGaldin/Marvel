package com.gateway.marvel.feature_characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.ResultResponse
import com.gateway.marvel.core.utils.CommonConstants
import com.gateway.marvel.repo_characters.CharactersRepository
import com.gateway.marvel.ui_kit.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharactersScreenState(
    val characters: List<Character>? = null,
    override val isRefreshing: Boolean = true,
    override var errorMessage: String? = null,
    override var throwable: Throwable? = null
): ScreenState()

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

        val charactersResult = charactersRepository.fetchCharacters(_offset.intValue)

        _charactersScreenState.value = _charactersScreenState.value.copy(
            characters = when (charactersResult) {
                is ResultResponse.Success -> charactersResult.data?.results
                is ResultResponse.Error -> null
            },
            isRefreshing = false,
            errorMessage = when {
                charactersResult is ResultResponse.Error -> charactersResult.errorMsg
                else -> null
            },
            throwable = when {
                charactersResult is ResultResponse.Error -> charactersResult.throwable
                else -> null
            }
        )
    }

    fun nextCharacters() {
        _offset.intValue += CommonConstants.LIMIT_CHARACTER

        viewModelScope.launch {
            fetchCharacters()
        }
    }

    fun previousCharacters() {
        _offset.intValue -= CommonConstants.LIMIT_CHARACTER

        viewModelScope.launch {
            fetchCharacters()
        }
    }

    fun clearError() {
        _charactersScreenState.value = _charactersScreenState.value.copy(
            errorMessage = null,
            throwable = null
        )
    }
}