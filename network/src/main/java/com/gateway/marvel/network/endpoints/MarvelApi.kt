package com.gateway.marvel.network.endpoints

import com.gateway.marvel.core.MarvelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    fun getCharacters(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String
    ): Call<MarvelResponse>
}
