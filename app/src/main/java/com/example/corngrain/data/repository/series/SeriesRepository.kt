package com.example.corngrain.data.repository.series

import com.example.corngrain.data.db.entity.series.OnAirTodayEntity

interface SeriesRepository {
    suspend fun getOnAirTodaySeries(): List<OnAirTodayEntity>
}