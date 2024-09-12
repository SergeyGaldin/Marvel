package com.gateway.marvel.network.endpoints

import com.gateway.marvel.core.Character
import com.gateway.marvel.network.dto.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(): Response<MarvelResponse<List<Character>>>
}
