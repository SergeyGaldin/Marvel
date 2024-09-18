package com.gateway.marvel.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.core.dto.Comic
import com.gateway.marvel.local_db.converter.ThumbnailConverter
import com.gateway.marvel.local_db.dao.CharacterDao
import com.gateway.marvel.local_db.dao.ComicDao

@Database(
    entities = [Character::class, Comic::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ThumbnailConverter::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao
}