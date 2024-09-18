package com.gateway.marvel.network.endpoints

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.core.utils.CommonConstants
import com.gateway.marvel.network.utils.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = CommonConstants.LIMIT_CHARACTERS,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<List<Character>>>

    @GET("comics")
    suspend fun getComics(
        @Query("limit") limit: Int = CommonConstants.LIMIT_CHARACTERS,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<List<Comic>>>
}
