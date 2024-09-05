package com.gateway.marvel.first

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

fun fetchCharacters() {
    val publicKey = "4827ca7051c9910f84635725ee432b1b"
    val privateKey = "82bfb939fd2f164485cb137d5fd9d5d26a8cd98f"
    val ts = Date().time.toString()
    val hash = RetrofitInstance.getMd5Hash(ts + privateKey + publicKey)

    val api = RetrofitInstance.api

    val call = api.getCharacters(apikey = publicKey, hash = hash, ts = ts)
    call.enqueue(object : Callback<MarvelResponse> {
        override fun onResponse(call: Call<MarvelResponse>, response: Response<MarvelResponse>) {
            if (response.isSuccessful) {
                val marvelResponse = response.body()
                if (marvelResponse != null) {
                    // Успешный ответ, используйте данные
                    val characters = marvelResponse.data.results
                    for (character in characters) {
                        println("Character name: ${character.name}")
                    }
                }
            } else {
                // Обработка ошибок
                println("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<MarvelResponse>, t: Throwable) {
            // Обработка ошибок сети
            println("Network error: ${t.message}")
        }
    })
}
