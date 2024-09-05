package com.gateway.marvel.first

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

object RetrofitInstance {
    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    fun getMd5Hash(value: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(value.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    val api: MarvelApi by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
    }
}
