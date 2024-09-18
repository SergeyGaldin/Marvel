package com.gateway.marvel.network.utils

import com.gateway.marvel.network.dto.MarvelResponse
import com.gateway.marvel.network.dto.MarvelResponseError
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

inline fun <reified T> getDataFromNetwork(
    execute: () -> Response<MarvelResponse<T>>
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