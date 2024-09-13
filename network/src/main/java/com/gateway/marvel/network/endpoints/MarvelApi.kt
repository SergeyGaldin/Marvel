package com.gateway.marvel.network.endpoints

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<MarvelResponse<List<Character>>>
}
