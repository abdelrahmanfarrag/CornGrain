package com.example.corngrain.data.network.outsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.response.Playing
import com.example.corngrain.data.network.response.Popular
import com.example.corngrain.data.network.response.TopRated
import com.example.corngrain.data.network.response.Upcoming
import com.example.corngrain.utilities.NoNetworkException

class TmdbNetworkLayerImpl(private val api: TmdbApi) : TmdbNetworkLayer {


    private val _mutableLatestMoviesData = MutableLiveData<Popular>()
    private val _mutableUpcomingMoviesData = MutableLiveData<Upcoming>()
    private val _mutableTopRatedMoviesData = MutableLiveData<TopRated>()
    private val _mutablePlayingMoviesData = MutableLiveData<Playing>()

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
}