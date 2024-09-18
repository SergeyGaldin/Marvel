package com.gateway.marvel.network.dto

data class MarvelResponse<T>(
    val data: DataContainer<T>
)

data class DataContainer<T>(
    val total: Int,
    val results: T
)

data class MarvelResponseError(
    val code: String,
    val message: String
)