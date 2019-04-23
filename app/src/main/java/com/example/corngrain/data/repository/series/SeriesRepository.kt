package com.example.corngrain.data.repository.series

import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity

interface SeriesRepository {
    suspend fun getOnAirTodaySeries(): List<OnAirTodayEntity>
    suspend fun getPopularSeries():List<PopularSeriesEntity>
}