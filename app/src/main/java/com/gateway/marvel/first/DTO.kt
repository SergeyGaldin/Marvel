package com.gateway.marvel.first

data class MarvelResponse(
    val data: DataContainer
)

data class DataContainer(
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
)
