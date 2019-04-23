package com.example.corngrain.data.network.outsource

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.Popular
import com.example.corngrain.data.network.response.Upcoming

interface TmdbNetworkLayer {

    val latestMovies: LiveData<Popular>

    suspend fun loadLatestMovies()

    val upcomingMovies:LiveData<Upcoming>

    suspend fun loadUpcomingMovies()
}