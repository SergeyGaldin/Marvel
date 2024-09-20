package com.gateway.marvel

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.mock.MockEntity
import com.gateway.marvel.local_db.dao.CharacterDao
import com.gateway.marvel.local_db.dto.CharacterLocal
import com.gateway.marvel.local_db.mapper.CharacterLocalMapper
import com.gateway.marvel.network.dto.CharacterNetwork
import com.gateway.marvel.network.dto.DataContainer
import com.gateway.marvel.network.dto.MarvelResponse
import com.gateway.marvel.network.endpoints.MarvelApi
import com.gateway.marvel.network.mapper.CharacterNetworkMapper
import com.gateway.marvel.repo_characters.CharactersRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class CharactersRepositoryTest {
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var characterLocalMapper: CharacterLocalMapper
    private lateinit var characterNetworkMapper: CharacterNetworkMapper
    private lateinit var marvelApi: MarvelApi
    private lateinit var characterDao: CharacterDao

    private val offset = 0
    private val limit = 20

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        marvelApi = mock()
        characterDao = mock()
        characterLocalMapper = mock()
        characterNetworkMapper = mock()
        charactersRepository = CharactersRepository(
            marvelApi,
            characterDao,
            characterLocalMapper,
            characterNetworkMapper
        )
    }

    private fun prepareMockCharacter(): Character = MockEntity.mockCharacter()

    private fun prepareMockLocalCharacter(
        character: Character = prepareMockCharacter()
    ): CharacterLocal = CharacterLocalMapper().anotherToDomainMap(character)

    private fun prepareMockNetworkCharacter(
        character: Character = prepareMockCharacter()
    ): CharacterNetwork = CharacterNetworkMapper().anotherToDomainMap(character)

    @Test
    fun `fetchCharacters should return local data when isGetLocalData is true`() = runBlocking {
        // Arrange
        val localCharacters = listOf(prepareMockLocalCharacter())

        whenever(characterDao.getCharacters()).thenReturn(localCharacters)

        // Act
        val result = charactersRepository.fetchCharacters(true, offset, limit)

        // Assert
        assertNotNull(result.characters)
        assertNotNull(result.total)
    }

    @Test
    fun `fetchCharacters returns characters on successful API call when isGetLocalData is false`() =
        runBlocking {
            // Arrange
            val characters = listOf(prepareMockCharacter())
            val localCharacters = listOf(prepareMockLocalCharacter())
            val networkCharacters = listOf(prepareMockNetworkCharacter())
            val marvelDataResponse = DataContainer(1, networkCharacters)
            val marvelResponse = MarvelResponse(marvelDataResponse)
            val response = Response.success(marvelResponse)

            whenever(characterDao.getCharacters()).thenReturn(localCharacters)

            whenever(marvelApi.getCharacters(limit, offset)).thenReturn(response)

            whenever(characterNetworkMapper.domainToAnotherMaps(networkCharacters))
                .thenReturn(characters)

            // Act
            val result = charactersRepository.fetchCharacters(false, offset, limit)

            // Assert
            assertNotNull(result)
            assertEquals(characters, result.characters)
            assertEquals(1, result.total)
            assertNull(result.errorMessage)
            assertNull(result.throwable)
        }

    @Test
    fun `fetchCharacters returns error when API call fails when isGetLocalData is false`() =
        runBlocking {
            // Arrange
            whenever(characterDao.getCharacters()).thenReturn(emptyList())

            val errorJson = """{"message": "Bad Request", "code": 400}"""
            val errorResponse = Response.error<MarvelResponse<List<CharacterNetwork>>>(
                400,
                errorJson.toResponseBody("application/json".toMediaTypeOrNull())
            )
            whenever(marvelApi.getCharacters(limit, offset)).thenReturn(errorResponse)

            // Act
            val result = charactersRepository.fetchCharacters(false, offset, limit)

            // Assert
            assertNotNull(result)
            assertNotNull(result.errorMessage)
            assertNull(result.characters)
            assertNull(result.total)
            assertNull(result.throwable)
        }

    @Test
    fun `fetchCharacters returns throwable when API call throws exception when isGetLocalData is false`() =
        runBlocking {
            // Arrange
            whenever(characterDao.getCharacters()).thenReturn(emptyList())

            val exception = RuntimeException("RuntimeException")
            whenever(marvelApi.getCharacters(limit, offset)).thenThrow(exception)

            // Act
            val result = charactersRepository.fetchCharacters(false, offset, limit)

            // Assert
            assertNotNull(result)
            assertNull(result.characters)
            assertNull(result.total)
            assertNotNull(result.errorMessage)
            assertNotNull(result.throwable)
        }

    @Test
    fun `addFavoriteCharacter should insert character into database`() = runTest {
        // Arrange
        val character = prepareMockCharacter()
        val localCharacter = prepareMockLocalCharacter()

        whenever(characterLocalMapper.anotherToDomainMap(character)).thenReturn(localCharacter)

        // Act
        charactersRepository.addFavoriteCharacter(character)

        // Assert
        verify(characterDao).insert(localCharacter)
    }

    @Test
    fun `deleteFavoriteCharacter should remove character from database`() = runTest {
        // Arrange
        val character = prepareMockCharacter()
        val localCharacter = prepareMockLocalCharacter()

        whenever(characterLocalMapper.anotherToDomainMap(character)).thenReturn(localCharacter)

        // Act
        charactersRepository.deleteFavoriteCharacter(character)

        // Assert
        verify(characterDao).delete(localCharacter)
    }
}