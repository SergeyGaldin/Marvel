package com.gateway.marvel.local_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gateway.marvel.local_db.dto.ComicLocal

@Dao
interface ComicDao {
    @Query("SELECT * FROM Comic")
    suspend fun getComics(): List<ComicLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comic: ComicLocal)

    @Delete
    suspend fun delete(comic: ComicLocal)
}