package com.example.corngrain.data.db.dao.series

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity

@Dao
interface OnAirDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertingTvSeriesOnlineToday(entries: List<OnAirTodayEntity>)

    @Query("select * from tv_today")
    fun getOnAirToday(): List<OnAirTodayEntity>
}