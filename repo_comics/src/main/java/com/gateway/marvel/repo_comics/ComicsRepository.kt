package com.gateway.marvel.repo_comics

import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.local_db.dao.ComicDao
import com.gateway.marvel.network.endpoints.MarvelApi
import com.gateway.marvel.network.utils.ResultResponse
import com.gateway.marvel.network.utils.getDataFromNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ComicsData(
    val comics: List<Comic>? = null,
    val total: Int? = null,
    val errorMessage: String? = null,
    val throwable: Throwable? = null
)

class ComicsRepository @Inject constructor(
    private val marvelApi: MarvelApi,
    private val comicDao: ComicDao
) {
    suspend fun fetchComics(
        isGetLocalData: Boolean,
        offset: Int,
        limit: Int
    ): ComicsData {
        val localComics = getLocalComics()
        if (isGetLocalData) return ComicsData(
            comics = localComics,
            total = localComics.size
        )

        val remoteComicsResult = getRemoteComics(offset, limit)

        val remoteComics = when (remoteComicsResult) {
            is ResultResponse.Success -> remoteComicsResult.data?.results?.toMutableList()
            is ResultResponse.Error -> null
        }?.map { remoteComic ->
            val localComic = localComics.firstOrNull { localComic ->
                remoteComic.id == localComic.id
            }
            remoteComic.isFavorite = localComic != null
            remoteComic
        }

        val remoteTotal = when (remoteComicsResult) {
            is ResultResponse.Success -> remoteComicsResult.data?.total
            is ResultResponse.Error -> null
        }

        val errorMessage = when {
            remoteComicsResult is ResultResponse.Error -> remoteComicsResult.errorMsg
            else -> null
        }

        val throwable = when {
            remoteComicsResult is ResultResponse.Error -> remoteComicsResult.throwable
            else -> null
        }

        return ComicsData(
            comics = remoteComics,
            total = remoteTotal,
            errorMessage = errorMessage,
            throwable = throwable
        )
    }

    private suspend fun getRemoteComics(
        offset: Int,
        limit: Int
    ) = getDataFromNetwork<List<Comic>> {
        marvelApi.getComics(
            limit = limit,
            offset = offset
        )
    }

    private suspend fun getLocalComics() = withContext(Dispatchers.IO) {
        comicDao.getComics()
    }

    suspend fun addFavoriteComic(comic: Comic) = withContext(Dispatchers.IO) {
        comic.isFavorite = true
        comicDao.insert(comic)
    }

    suspend fun deleteFavoriteComic(comic: Comic) = withContext(Dispatchers.IO) {
        comic.isFavorite = false
        comicDao.delete(comic)
    }
}