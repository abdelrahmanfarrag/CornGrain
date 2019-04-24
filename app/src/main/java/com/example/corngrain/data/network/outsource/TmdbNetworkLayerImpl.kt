package com.example.corngrain.data.network.outsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.response.movies.Playing
import com.example.corngrain.data.network.response.movies.Popular
import com.example.corngrain.data.network.response.movies.TopRated
import com.example.corngrain.data.network.response.movies.Upcoming
import com.example.corngrain.data.network.response.series.OnAirToday
import com.example.corngrain.data.network.response.series.PopularSeries
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.data.network.response.series.TopRatedSeries
import com.example.corngrain.utilities.NoNetworkException

class TmdbNetworkLayerImpl(private val api: TmdbApi) : TmdbNetworkLayer {


    private val _mutableLatestMoviesData = MutableLiveData<Popular>()
    private val _mutableUpcomingMoviesData = MutableLiveData<Upcoming>()
    private val _mutableTopRatedMoviesData = MutableLiveData<TopRated>()
    private val _mutablePlayingMoviesData = MutableLiveData<Playing>()
    private val _mutableOnAirSeriesData = MutableLiveData<OnAirToday>()
    private val _mutablePopularSeriesData = MutableLiveData<PopularSeries>()
    private val _mutableSerieDetail = MutableLiveData<SerieDetail>()
    private val _mutableTopRatedSeries = MutableLiveData<TopRatedSeries>()

    override val latestMovies: LiveData<Popular>
        get() = _mutableLatestMoviesData

    override suspend fun loadLatestMovies() {
        try {
            val latestMoviesJob = api.getLatestMoviesAsync().await()
            _mutableLatestMoviesData.postValue(latestMoviesJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }

    override val upcomingMovies: LiveData<Upcoming>
        get() = _mutableUpcomingMoviesData

    override suspend fun loadUpcomingMovies() {
        try {
            val upcomingJob = api.getUpcomingMoviesAsync().await()
            _mutableUpcomingMoviesData.postValue(upcomingJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val topRatedMovies: LiveData<TopRated>
        get() = _mutableTopRatedMoviesData

    override suspend fun loadTopRatedMovies() {
        try {
            val topRatedJob = api.getTopRatedMoviesAsync().await()
            _mutableTopRatedMoviesData.postValue(topRatedJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val playingMovies: LiveData<Playing>
        get() = _mutablePlayingMoviesData

    override suspend fun loadPlayingMovies() {
        try {
            val playingJob = api.getPlayingMoviesAsync().await()
            _mutablePlayingMoviesData.postValue(playingJob)

        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val onAirToday: LiveData<OnAirToday>
        get() = _mutableOnAirSeriesData

    override suspend fun loadOnAirToday() {
        try {
            val onAirData = api.getTvOnAirTodayAsync().await()
            _mutableOnAirSeriesData.postValue(onAirData)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }

    override val popularSeries: LiveData<PopularSeries>
        get() = _mutablePopularSeriesData

    override suspend fun loadPopularSeries() {
        try {
            val popularSeriesData = api.getTvPopularSeriesAsync().await()
            _mutablePopularSeriesData.postValue(popularSeriesData)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }

    override val serieDetail: LiveData<SerieDetail>
        get() = _mutableSerieDetail

    override suspend fun loadSerieDetail(id: Int) {
        try {
            val serieDetail = api.getSerieDetailAsync(id).await()
            _mutableSerieDetail.postValue(serieDetail)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val topRatedSeries: LiveData<TopRatedSeries>
        get() = _mutableTopRatedSeries

    override suspend fun loadTopRatedSeries() {
        try {
            val topRatedSeries = api.getTopRatedSeriesAsync().await()
            _mutableTopRatedSeries.postValue(topRatedSeries)
        } catch (e: Exception) {
            Log.d("noConnection", "No network")

        }
    }
}