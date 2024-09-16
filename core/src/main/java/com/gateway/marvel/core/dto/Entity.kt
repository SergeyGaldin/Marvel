package com.gateway.marvel.core.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
)