package com.gateway.marvel.network.utils

import com.gateway.marvel.network.dto.DataContainer

sealed class ResultResponse<out T> {
    data class Success<T>(val data: DataContainer<T>?) : ResultResponse<T>()
    data class Error(val throwable: Throwable?, val errorMsg: String?) : ResultResponse<Nothing>()
}