package com.example.corngrain.data.repository.series

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.data.network.response.series.*

interface SeriesRepository {
    suspend fun getOnAirTodaySeries(page: Int): LiveData<OnAirToday>
    suspend fun getPopularSeries(page: Int): LiveData<PopularSeries>
    suspend fun getRatedSeries(page:Int): LiveData<TopRatedSeries>
    suspend fun getInshowSeries(page: Int): LiveData<SerieCurrentlyShowing>
    suspend fun getSerieCast(id: Int): LiveData<Credits>
    suspend fun getSerieDetail(id: Int): LiveData<SerieDetail>
    suspend fun getSerieReviews(id: Int): LiveData<Videos>

}