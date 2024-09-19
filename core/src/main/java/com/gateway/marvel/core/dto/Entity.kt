package com.gateway.marvel.core.dto

data class Character(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
    var isFavorite: Boolean = false
)

data class Comic(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    var isFavorite: Boolean = false
)

data class Thumbnail(
    val path: String,
    val extension: String
)