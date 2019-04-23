package com.example.corngrain.data.network.outsource

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.Popular
import com.example.corngrain.data.network.response.TopRated
import com.example.corngrain.data.network.response.Upcoming

interface TmdbNetworkLayer {
    //Popular Movies
    val latestMovies: LiveData<Popular>
    suspend fun loadLatestMovies()

    //Upcoming
    val upcomingMovies: LiveData<Upcoming>
    suspend fun loadUpcomingMovies()

    //TopRated
    val topRatedMovies : LiveData<TopRated>
    suspend fun loadTopRatedMovies()
}