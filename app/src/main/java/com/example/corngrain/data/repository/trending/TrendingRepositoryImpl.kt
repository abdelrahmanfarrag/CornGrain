package com.example.corngrain.data.repository.trending

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrendingRepositoryImpl(val api: TmdbNetworkLayer) : TrendingRepository {
    override suspend fun loadTrendingSeries(): LiveData<SeriesAndTvShows> {
        return withContext(Dispatchers.IO) {
            api.getTrendingShows()
            return@withContext api.trendingSeries
        }
    }

    override suspend fun loadTrendingMovies(): LiveData<Trending> {
        return withContext(Dispatchers.IO) {
            api.getTrendingMovies()
            return@withContext api.trending
        }
    }
}