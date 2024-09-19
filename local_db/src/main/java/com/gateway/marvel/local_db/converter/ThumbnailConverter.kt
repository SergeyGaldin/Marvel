package com.gateway.marvel.local_db.converter

import androidx.room.TypeConverter
import com.gateway.marvel.local_db.dto.ThumbnailLocal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ThumbnailConverter {
    @TypeConverter
    fun fromThumbnail(thumbnail: ThumbnailLocal): String {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnailString: String): ThumbnailLocal {
        val thumbnailType = object : TypeToken<ThumbnailLocal>() {}.type
        return Gson().fromJson(thumbnailString, thumbnailType)
    }
}