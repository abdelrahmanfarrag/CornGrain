package com.example.corngrain.data.repository.series

import com.example.corngrain.data.db.dao.series.OnAirDao
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesRepositoryImpl(
    private val networkOutSource: TmdbNetworkLayer,
    private val onAirDao: OnAirDao
) : SeriesRepository {

    init {
        networkOutSource.apply {
            onAirToday.observeForever { todaySeries ->
                persistingTodaySeries(todaySeries.results)
            }
        }
    }

    override suspend fun getOnAirTodaySeries(): List<OnAirTodayEntity> {
        return withContext(Dispatchers.IO) {
            loadOnAirTodayFromNetworkCall()
            return@withContext onAirDao.getOnAirToday()
        }
    }

    private fun persistingTodaySeries(entries: List<OnAirTodayEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            onAirDao.insertingTvSeriesOnlineToday(entries)
        }

    }


    private suspend fun loadOnAirTodayFromNetworkCall() {
        networkOutSource.loadOnAirToday()
    }

}