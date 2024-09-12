package com.gateway.marvel.ui_kit.utils

open class ScreenState(
    open val isRefreshing: Boolean = false,
    open var errorMessage: String? = null,
    open var throwable: Throwable? = null
)