package com.example.corngrain.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corngrain.data.db.entity.PlayingEntity
import com.example.corngrain.data.db.entity.PopularEntity

@Dao
interface PlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlayingMovies(entries: List<PlayingEntity>)

    @Query("select * from playing")
    fun getNowPlayingMovies(): List<PlayingEntity>
}