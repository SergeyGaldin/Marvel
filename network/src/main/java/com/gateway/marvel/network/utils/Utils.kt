package com.gateway.marvel.network.utils

import com.gateway.marvel.network.dto.DataContainer
import com.gateway.marvel.network.dto.MarvelResponse
import com.gateway.marvel.network.dto.MarvelResponseError
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.math.BigInteger
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.MessageDigest
import javax.net.ssl.SSLHandshakeException

sealed class ResultResponse<out T> {
    data class Success<T>(val data: DataContainer<T>?) : ResultResponse<T>()
    data class Error(val throwable: Throwable?, val errorMsg: String?) : ResultResponse<Nothing>()
}

suspend fun <T> getDataFromNetwork(
    execute: suspend () -> Response<MarvelResponse<T>>
): ResultResponse<T> {
    try {
        val response = execute()
        if (response.isSuccessful) {
            val successBody = response.body()
            return ResultResponse.Success(successBody?.data)
        } else {
            val error = parseError(response)
            return ResultResponse.Error(null, error?.message)
        }
    } catch (throwable: Throwable) {
        return handleExceptions(throwable)
    }
}

fun parseError(response: Response<*>): MarvelResponseError? {
    return try {
        val errorBody = response.errorBody()?.string()
        errorBody?.let {
            Gson().fromJson(it, MarvelResponseError::class.java)
        }
    } catch (e: JsonSyntaxException) {
        null
    }
}

fun handleExceptions(throwable: Throwable): ResultResponse.Error {
    return when (throwable) {
        is SocketTimeoutException -> {
            ResultResponse.Error(throwable, "Тайм-аут соединения")
        }

        is ConnectException -> {
            ResultResponse.Error(throwable, "Сервер недоступен, проверьте ваше подключение")
        }

        is UnknownHostException -> {
            ResultResponse.Error(throwable, "Хост не найден, проверьте ваше подключение")
        }

        is SSLHandshakeException -> {
            ResultResponse.Error(throwable, "Проблемы с SSL-сертификатом")
        }

        is IOException -> {
            ResultResponse.Error(throwable, "Ошибка при выполнении запроса")
        }

        else -> {
            ResultResponse.Error(throwable, "Непредвиденная ошибка")
        }
    }
}