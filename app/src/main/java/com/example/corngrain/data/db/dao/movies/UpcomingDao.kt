package com.example.corngrain.data.db.dao.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corngrain.data.db.entity.movies.UpcomingEntity


@Dao
interface UpcomingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertingPopularMovies(entries: List<UpcomingEntity>)

    @Query("select * from upcoming ")
    fun getPopularMovies(): List<UpcomingEntity>
}