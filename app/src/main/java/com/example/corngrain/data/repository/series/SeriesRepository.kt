package com.example.corngrain.data.repository.series

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.series.SerieDetail

interface SeriesRepository {
    suspend fun getOnAirTodaySeries(): List<OnAirTodayEntity>
    suspend fun getPopularSeries():MutableList<PopularSeriesEntity>
    suspend fun getSerieDetail(id:Int):LiveData<SerieDetail>
}