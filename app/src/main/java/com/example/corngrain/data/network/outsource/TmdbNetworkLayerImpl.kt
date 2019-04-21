package com.example.corngrain.data.network.outsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.response.LatestMovies
import com.example.corngrain.utilities.NoNetworkException

class TmdbNetworkLayerImpl(val api: TmdbApi) : TmdbNetworkLayer {

    private val _mutableLatestMoviesData = MutableLiveData<LatestMovies>()

    override val latestMovies: LiveData<LatestMovies>
        get() = _mutableLatestMoviesData

    override suspend fun loadLatestMovies() {
        try {
            val latestMoviesJob = api.getLatestMovies().await()
            _mutableLatestMoviesData.postValue(latestMoviesJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }
}