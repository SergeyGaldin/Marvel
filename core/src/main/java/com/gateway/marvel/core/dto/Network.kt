package com.gateway.marvel.core.dto

sealed class ResultResponse<out T> {
    data class Success<T>(val data: DataContainer<T>?) : ResultResponse<T>()
    data class Error(val throwable: Throwable?, val errorMsg: String?) : ResultResponse<Nothing>()
}

data class MarvelResponse<T>(
    val data: DataContainer<T>
)

data class DataContainer<T>(
    val results: T
)

data class MarvelResponseError(
    val code: String,
    val message: String
)