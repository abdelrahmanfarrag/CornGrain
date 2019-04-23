package com.example.corngrain.data.db.dao.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corngrain.data.db.entity.movies.TopRatedEntity

@Dao
interface TopRatedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRatedMovies(entries: List<TopRatedEntity>)

    @Query("select * from top_rated")
    fun getTopRatedMovies(): List<TopRatedEntity>
}