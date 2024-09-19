package com.gateway.marvel.local_db.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("character")
data class CharacterLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val thumbnail: ThumbnailLocal,
    var isFavorite: Boolean = false
)

@Entity("comic")
data class ComicLocal(
    @PrimaryKey val id: Int,
    val title: String,
    val thumbnail: ThumbnailLocal,
    var isFavorite: Boolean = false
)

data class ThumbnailLocal(
    val path: String,
    val extension: String
)