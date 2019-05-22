package com.example.corngrain.data.repository.series

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.dao.series.OnAirDao
import com.example.corngrain.data.db.dao.series.PopularSerieDao
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.data.network.response.series.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesRepositoryImpl(
    private val networkOutSource: TmdbNetworkLayer,
    private val onAirDao: OnAirDao,
    private val popularSerieDao: PopularSerieDao
) : SeriesRepository {
    override suspend fun getSerieReviews(id: Int): LiveData<Videos> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadSerieReviews(id)
            return@withContext networkOutSource.serieReviews
        }
    }

    override suspend fun getSerieCast(id: Int): LiveData<Credits> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadSerieCredits(id)
            return@withContext networkOutSource.serieCredits
        }
    }

    override suspend fun getInshowSeries(page: Int): LiveData<SerieCurrentlyShowing> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadInshowSeries(page)
            return@withContext networkOutSource.currentlyViewingSeries
        }
    }

    override suspend fun getRatedSeries(page: Int): LiveData<TopRatedSeries> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadTopRatedSeries(page)
            return@withContext networkOutSource.topRatedSeries
        }
    }

    override suspend fun getSerieDetail(id: Int): LiveData<SerieDetail> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadSerieDetail(id)
            return@withContext networkOutSource.serieDetail
        }

    }

    override suspend fun getOnAirTodaySeries(page: Int): LiveData<OnAirToday> {
        return withContext(Dispatchers.IO) {
            loadOnAirTodayFromNetworkCall(page)
            return@withContext networkOutSource.onAirToday
        }
    }

    override suspend fun getPopularSeries(page: Int): LiveData<PopularSeries> {
        return withContext(Dispatchers.IO) {
            networkOutSource.loadPopularSeries(page)
            return@withContext networkOutSource.popularSeries
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

    private suspend fun loadOnAirTodayFromNetworkCall(page: Int) {
        networkOutSource.loadOnAirToday(page)
    }

    private suspend fun loadPopularSeriesFromNetworkCall(page: Int) {
        networkOutSource.loadPopularSeries(page)
    }

}