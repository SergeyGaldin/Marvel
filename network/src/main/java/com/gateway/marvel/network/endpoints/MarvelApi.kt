package com.gateway.marvel.network.endpoints

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.MarvelResponse
import com.gateway.marvel.core.utils.CommonConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = CommonConstants.LIMIT_CHARACTER,
        @Query("offset") offset: Int
    ): Response<MarvelResponse<List<Character>>>
}
