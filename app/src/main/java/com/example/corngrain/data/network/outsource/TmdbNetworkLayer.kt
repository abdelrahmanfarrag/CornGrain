package com.example.corngrain.data.network.outsource

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.Popular

interface TmdbNetworkLayer {

    val latestMovies: LiveData<Popular>

    suspend fun loadLatestMovies()
}