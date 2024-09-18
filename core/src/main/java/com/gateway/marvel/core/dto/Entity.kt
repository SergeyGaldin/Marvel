package com.gateway.marvel.core.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val thumbnail: Thumbnail,

    var isFavorite: Boolean = false
)

@Entity
data class Comic(
    @PrimaryKey val id: Int,
    val title: String,
    val thumbnail: Thumbnail,

    var isFavorite: Boolean = false
)

data class Thumbnail(
    val path: String,
    val extension: String
)