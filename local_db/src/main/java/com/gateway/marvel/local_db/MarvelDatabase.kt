package com.gateway.marvel.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gateway.marvel.core.dto.Character
import com.gateway.marvel.local_db.dao.CharacterDao

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = true
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}