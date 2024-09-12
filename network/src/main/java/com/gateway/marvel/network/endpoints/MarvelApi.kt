package com.gateway.marvel.network.endpoints

import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(): Response<MarvelResponse<List<Character>>>
}
