package com.example.corngrain.data.repository.series

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.series.SerieCurrentlyShowing
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.data.network.response.series.TopRatedSeries

interface SeriesRepository {
    suspend fun getOnAirTodaySeries(): List<OnAirTodayEntity>
    suspend fun getPopularSeries(): MutableList<PopularSeriesEntity>
    suspend fun getSerieDetail(id: Int): LiveData<SerieDetail>
    suspend fun getRatedSeries(page: Int = 1): LiveData<TopRatedSeries>
    suspend fun getInshowSeries(): LiveData<SerieCurrentlyShowing>
}