package com.gateway.marvel

import com.gateway.marvel.core.mock.MockEntity
import com.gateway.marvel.local_db.dao.CharacterDao
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

    @Test
    fun `fetchCharacters should return local data when isGetLocalData is true`() = runBlocking {
        // Arrange
        val mockCharacter = MockEntity.mockCharacter()
        val localCharacters = listOf(CharacterLocalMapper().anotherToDomainMap(mockCharacter))

        whenever(characterDao.getCharacters()).thenReturn(localCharacters)

        // Act
        val result = charactersRepository.fetchCharacters(true, 0, 20)

        // Assert
        assertNotNull(result.characters)
        assertNotNull(result.total)
    }

    @Test
    fun `fetchCharacters returns characters on successful API call when isGetLocalData is false`() =
        runBlocking {
            // Arrange
            val offset = 0
            val limit = 20
            val mockCharacter = MockEntity.mockCharacter()
            val localCharacters = listOf(CharacterLocalMapper().anotherToDomainMap(mockCharacter))
            val networkCharacters = listOf(
                CharacterNetworkMapper()
                    .anotherToDomainMap(mockCharacter)
            )
            val marvelDataResponse = DataContainer(1, networkCharacters)
            val marvelResponse = MarvelResponse(marvelDataResponse)

            whenever(characterDao.getCharacters()).thenReturn(localCharacters)

            val response = Response.success(marvelResponse)
            whenever(marvelApi.getCharacters(limit, offset)).thenReturn(response)

            val mappedCharacters = listOf(mockCharacter)
            whenever(characterNetworkMapper.domainToAnotherMaps(networkCharacters))
                .thenReturn(mappedCharacters)

            // Act
            val result = charactersRepository.fetchCharacters(false, offset, limit)

            // Assert
            assertNotNull(result)
            assertEquals(mappedCharacters, result.characters)
            assertEquals(1, result.total)
            assertNull(result.errorMessage)
            assertNull(result.throwable)
        }

    @Test
    fun `fetchCharacters returns error when API call fails when isGetLocalData is false`() =
        runBlocking {
            // Arrange
            val offset = 0
            val limit = 20

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
            val offset = 0
            val limit = 20

            whenever(characterDao.getCharacters()).thenReturn(emptyList())

            val exception = RuntimeException("Network error")
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
        val mockCharacter = MockEntity.mockCharacter()
        val mappedCharacter = CharacterLocalMapper().anotherToDomainMap(mockCharacter)

        whenever(characterLocalMapper.anotherToDomainMap(mockCharacter)).thenReturn(mappedCharacter)

        // Act
        charactersRepository.addFavoriteCharacter(mockCharacter)

        // Assert
        verify(characterDao).insert(mappedCharacter)
    }

    @Test
    fun `deleteFavoriteCharacter should remove character from database`() = runTest {
        // Arrange
        val mockCharacter = MockEntity.mockCharacter()
        val mappedCharacter = CharacterLocalMapper().anotherToDomainMap(mockCharacter)

        whenever(characterLocalMapper.anotherToDomainMap(mockCharacter)).thenReturn(mappedCharacter)

        // Act
        charactersRepository.deleteFavoriteCharacter(mockCharacter)

        // Assert
        verify(characterDao).delete(mappedCharacter)
    }
}