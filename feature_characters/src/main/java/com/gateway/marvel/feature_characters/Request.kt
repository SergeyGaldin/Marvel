package com.gateway.marvel.feature_characters

import com.gateway.marvel.network.endpoints.MarvelApi
import com.gateway.marvel.network.utils.getDataFromNetwork

suspend fun fetchCharacters(
    marvelApi: MarvelApi
) {
    val characterResultResponse = getDataFromNetwork { marvelApi.getCharacters() }

    println(characterResultResponse)
}
