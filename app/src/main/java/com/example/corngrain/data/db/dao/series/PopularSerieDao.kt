package com.example.corngrain.data.db.dao.series

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity

@Dao
interface PopularSerieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularSeries(entries: List<PopularSeriesEntity>)

    @Query("select * from tv_popular")
    fun getTvPopularSeries(): List<PopularSeriesEntity>
}