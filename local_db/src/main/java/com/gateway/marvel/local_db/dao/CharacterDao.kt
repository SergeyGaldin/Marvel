package com.gateway.marvel.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gateway.marvel.core.dto.Character

@Dao
interface CharacterDao {
    @Insert
    fun insert(character: Character)

    @Query("SELECT * FROM character WHERE id = :id")
    fun getCharacter(id: Int): Character
}