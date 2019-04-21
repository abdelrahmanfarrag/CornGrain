package com.example.corngrain.data.network.outsource

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.LatestMovies

interface TmdbNetworkLayer {

    val latestMovies: LiveData<LatestMovies>

    suspend fun loadLatestMovies()
}