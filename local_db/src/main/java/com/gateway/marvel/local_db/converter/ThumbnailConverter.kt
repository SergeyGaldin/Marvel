package com.gateway.marvel.local_db.converter

import androidx.room.TypeConverter
import com.gateway.marvel.core.dto.Thumbnail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ThumbnailConverter {
    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnailString: String): Thumbnail {
        val thumbnailType = object : TypeToken<Thumbnail>() {}.type
        return Gson().fromJson(thumbnailString, thumbnailType)
    }
}