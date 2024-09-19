package com.gateway.marvel.repo_characters

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.local_db.dao.CharacterDao
import com.gateway.marvel.local_db.mapper.CharacterLocalMapper
import com.gateway.marvel.network.dto.CharacterNetwork
import com.gateway.marvel.network.endpoints.MarvelApi
import com.gateway.marvel.network.mapper.CharacterNetworkMapper
import com.gateway.marvel.network.utils.ResultResponse
import com.gateway.marvel.network.utils.getDataFromNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class CharactersData(
    val characters: List<Character>? = null,
    val total: Int? = null,
    val errorMessage: String? = null,
    val throwable: Throwable? = null
)

class CharactersRepository @Inject constructor(
    private val marvelApi: MarvelApi,
    private val characterDao: CharacterDao,
    private val characterLocalMapper: CharacterLocalMapper,
    private val characterNetworkMapper: CharacterNetworkMapper
) {
    suspend fun fetchCharacters(
        isGetLocalData: Boolean,
        offset: Int,
        limit: Int
    ): CharactersData {
        val localCharacters = getLocalCharacters()
        if (isGetLocalData) return CharactersData(
            characters = characterLocalMapper.domainToAnotherMaps(localCharacters),
            total = localCharacters.size
        )

        val remoteCharactersResult = getRemoteCharacters(offset, limit)

        val remoteCharacters = when (remoteCharactersResult) {
            is ResultResponse.Success -> remoteCharactersResult.data?.results?.toMutableList()
            is ResultResponse.Error -> null
        }

        val characters = remoteCharacters
            ?.let { characterNetworkMapper.domainToAnotherMaps(it) }
            ?.map {
                val localCharacter = localCharacters.firstOrNull { localCharacter ->
                    it.id == localCharacter.id
                }
                it.isFavorite = localCharacter != null
                it
            }

        val remoteTotal = when (remoteCharactersResult) {
            is ResultResponse.Success -> remoteCharactersResult.data?.total
            is ResultResponse.Error -> null
        }

        val errorMessage = when {
            remoteCharactersResult is ResultResponse.Error -> remoteCharactersResult.errorMsg
            else -> null
        }

        val throwable = when {
            remoteCharactersResult is ResultResponse.Error -> remoteCharactersResult.throwable
            else -> null
        }

        return CharactersData(
            characters = characters,
            total = remoteTotal,
            errorMessage = errorMessage,
            throwable = throwable
        )
    }

    private suspend fun getRemoteCharacters(
        offset: Int,
        limit: Int
    ) = getDataFromNetwork<List<CharacterNetwork>> {
        marvelApi.getCharacters(
            limit = limit,
            offset = offset
        )
    }

    private suspend fun getLocalCharacters() = withContext(Dispatchers.IO) {
        characterDao.getCharacters()
    }

    suspend fun addFavoriteCharacter(character: Character) = withContext(Dispatchers.IO) {
        character.isFavorite = true
        characterDao.insert(characterLocalMapper.anotherToDomainMap(character))
    }

    suspend fun deleteFavoriteCharacter(character: Character) = withContext(Dispatchers.IO) {
        character.isFavorite = false
        characterDao.delete(characterLocalMapper.anotherToDomainMap(character))
    }
}