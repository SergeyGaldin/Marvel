package com.gateway.marvel.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date

class AuthInterceptor(
    private val publicKey: String,
    private val privateKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url

        val ts = Date().time.toString()
        val hash = getMd5Hash(ts + privateKey + publicKey)

        val newUrl = url.newBuilder()
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("ts", ts)
            .addQueryParameter("hash", hash)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    private fun getMd5Hash(value: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(value.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}