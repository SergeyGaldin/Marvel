package com.gateway.marvel.local_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gateway.marvel.local_db.dto.CharacterLocal

@Dao
interface CharacterDao {
    @Query("SELECT * FROM Character")
    suspend fun getCharacters(): List<CharacterLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterLocal)

    @Delete
    suspend fun delete(character: CharacterLocal)
}