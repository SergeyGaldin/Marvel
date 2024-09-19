package com.gateway.marvel.network.dto

data class CharacterNetwork(
    val id: Int,
    val name: String,
    val thumbnail: ThumbnailNetwork
)

data class ComicNetwork(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailNetwork
)

data class ThumbnailNetwork(
    val path: String,
    val extension: String
)