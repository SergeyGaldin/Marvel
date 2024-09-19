package com.gateway.marvel.repo_comics

import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.local_db.dao.ComicDao
import com.gateway.marvel.local_db.mapper.ComicLocalMapper
import com.gateway.marvel.network.dto.ComicNetwork
import com.gateway.marvel.network.endpoints.MarvelApi
import com.gateway.marvel.network.mapper.ComicNetworkMapper
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
    private val comicDao: ComicDao,
    private val comicLocalMapper: ComicLocalMapper,
    private val comicNetworkMapper: ComicNetworkMapper
) {
    suspend fun fetchComics(
        isGetLocalData: Boolean,
        offset: Int,
        limit: Int
    ): ComicsData {
        val localComics = getLocalComics()
        if (isGetLocalData) return ComicsData(
            comics = comicLocalMapper.domainToAnotherMaps(localComics),
            total = localComics.size
        )

        val remoteComicsResult = getRemoteComics(offset, limit)

        val remoteComics = when (remoteComicsResult) {
            is ResultResponse.Success -> remoteComicsResult.data?.results?.toMutableList()
            is ResultResponse.Error -> null
        }

        val comics = remoteComics
            ?.let { comicNetworkMapper.domainToAnotherMaps(it) }
            ?.map {
                val localComic = localComics.firstOrNull { localComic ->
                    it.id == localComic.id
                }
                it.isFavorite = localComic != null
                it
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
            comics = comics,
            total = remoteTotal,
            errorMessage = errorMessage,
            throwable = throwable
        )
    }

    private suspend fun getRemoteComics(
        offset: Int,
        limit: Int
    ) = getDataFromNetwork<List<ComicNetwork>> {
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
        comicDao.insert(comicLocalMapper.anotherToDomainMap(comic))
    }

    suspend fun deleteFavoriteComic(comic: Comic) = withContext(Dispatchers.IO) {
        comic.isFavorite = false
        comicDao.delete(comicLocalMapper.anotherToDomainMap(comic))
    }
}