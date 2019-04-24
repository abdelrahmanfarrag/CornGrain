package com.example.corngrain.data.repository.series

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.dao.series.OnAirDao
import com.example.corngrain.data.db.dao.series.PopularSerieDao
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.series.SerieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesRepositoryImpl(
    private val networkOutSource: TmdbNetworkLayer,
    private val onAirDao: OnAirDao,
    private val popularSerieDao: PopularSerieDao
) : SeriesRepository {

    override suspend fun getSerieDetail(id:Int): LiveData<SerieDetail> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadSerieDetail(id)
            return@withContext networkOutSource.serieDetail
        }

    }


    init {
        networkOutSource.apply {
            onAirToday.observeForever { todaySeries ->
                persistingTodaySeries(todaySeries.results)
            }
            popularSeries.observeForever { popularSeries ->
                persistingPopularSeries(popularSeries.results)
            }
        }
    }

    override suspend fun getOnAirTodaySeries(): List<OnAirTodayEntity> {
        return withContext(Dispatchers.IO) {
            loadOnAirTodayFromNetworkCall()
            return@withContext onAirDao.getOnAirToday()
        }
    }

    override suspend fun getPopularSeries(): MutableList<PopularSeriesEntity> {
        return withContext(Dispatchers.IO) {
            loadPopularSeriesFromNetworkCall()
            return@withContext popularSerieDao.getTvPopularSeries()
        }
    }

    private fun persistingTodaySeries(entries: List<OnAirTodayEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            onAirDao.insertingTvSeriesOnlineToday(entries)
        }

    }

    private fun persistingPopularSeries(entries: List<PopularSeriesEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            popularSerieDao.insertPopularSeries(entries)
        }
    }

    private suspend fun loadOnAirTodayFromNetworkCall() {
        networkOutSource.loadOnAirToday()
    }

    private suspend fun loadPopularSeriesFromNetworkCall() {
        networkOutSource.loadPopularSeries()
    }

}