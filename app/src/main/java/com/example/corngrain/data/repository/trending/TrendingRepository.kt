package com.example.corngrain.data.repository.trending

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending

interface TrendingRepository {

    suspend fun loadTrendingMovies():LiveData<Trending>
        suspend fun loadTrendingSeries():LiveData<SeriesAndTvShows>
}