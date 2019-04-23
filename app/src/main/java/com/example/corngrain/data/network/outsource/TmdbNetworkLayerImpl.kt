package com.example.corngrain.data.network.outsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.response.Popular
import com.example.corngrain.data.network.response.Upcoming
import com.example.corngrain.utilities.NoNetworkException

class TmdbNetworkLayerImpl(val api: TmdbApi) : TmdbNetworkLayer {


    private val _mutableLatestMoviesData = MutableLiveData<Popular>()
    private val _mutableUpcomingMoviesData = MutableLiveData<Upcoming>()

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
}