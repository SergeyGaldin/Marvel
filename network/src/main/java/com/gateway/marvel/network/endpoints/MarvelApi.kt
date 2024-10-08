package com.gateway.marvel.network.endpoints

import com.gateway.marvel.network.dto.CharacterNetwork
import com.gateway.marvel.network.dto.ComicNetwork
import com.gateway.marvel.network.dto.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<List<CharacterNetwork>>>

    @GET("comics")
    suspend fun getComics(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<List<ComicNetwork>>>
}
