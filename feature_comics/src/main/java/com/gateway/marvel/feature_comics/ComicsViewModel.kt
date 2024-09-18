package com.gateway.marvel.feature_comics

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.local_preferences.AppSettings
import com.gateway.marvel.repo_comics.ComicsRepository
import com.gateway.marvel.ui_kit.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ComicsScreenState(
    val comics: List<Comic>? = null,
    val total: Int? = null,
    val isShowOnlyFavoritesComics: Boolean = false,
    override val isRefreshing: Boolean = true,
    override var errorMessage: String? = null,
    override var throwable: Throwable? = null
) : ScreenState()

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val comicsRepository: ComicsRepository,
    appSettings: AppSettings
) : ViewModel() {
    private val _comicsScreenState = mutableStateOf(ComicsScreenState())
    val comicsScreenState: State<ComicsScreenState> get() = _comicsScreenState

    private val _offset = mutableIntStateOf(appSettings.startOffsetComics)
    val offset: State<Int> get() = _offset

    private val _limit = mutableIntStateOf(appSettings.limitComics)
    val limit: State<Int> get() = _limit

    suspend fun fetchComic() {
        _comicsScreenState.value = _comicsScreenState.value.copy(isRefreshing = true)

        val comicsData = comicsRepository.fetchComics(
            isGetLocalData = _comicsScreenState.value.isShowOnlyFavoritesComics,
            offset = _offset.intValue,
            limit = _limit.intValue
        )

        _comicsScreenState.value = _comicsScreenState.value.copy(
            comics = comicsData.comics,
            total = comicsData.total,
            isRefreshing = false,
            errorMessage = comicsData.errorMessage,
            throwable = comicsData.throwable
        )
    }

    fun addFavoriteComic(comic: Comic) {
        _comicsScreenState.value = _comicsScreenState.value.copy(
            comics = _comicsScreenState.value.comics?.map {
                if (it.id == comic.id) it.copy(isFavorite = true) else it
            }
        )

        viewModelScope.launch {
            comicsRepository.addFavoriteComic(comic)
        }
    }

    fun deleteFavoriteComic(comic: Comic) {
        _comicsScreenState.value = _comicsScreenState.value.copy(
            comics = _comicsScreenState.value.comics?.map {
                if (it.id == comic.id) it.copy(isFavorite = false) else it
            }
        )

        viewModelScope.launch {
            comicsRepository.deleteFavoriteComic(comic)
        }
    }

    fun nextComics() {
        _offset.intValue += _limit.intValue
        getComics()
    }

    fun previousComics() {
        _offset.intValue -= _limit.intValue
        getComics()
    }

    fun clearError() {
        _comicsScreenState.value = _comicsScreenState.value.copy(
            errorMessage = null,
            throwable = null
        )
    }

    fun changeStateShowOnlyFavoritesComics() {
        _comicsScreenState.value = _comicsScreenState.value.copy(
            isShowOnlyFavoritesComics = !_comicsScreenState.value.isShowOnlyFavoritesComics
        )
        getComics()
    }

    private fun getComics() {
        viewModelScope.launch {
            fetchComic()
        }
    }
}