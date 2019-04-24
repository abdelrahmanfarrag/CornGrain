package com.example.corngrain.data.network.outsource

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.movies.Playing
import com.example.corngrain.data.network.response.movies.Popular
import com.example.corngrain.data.network.response.movies.TopRated
import com.example.corngrain.data.network.response.movies.Upcoming
import com.example.corngrain.data.network.response.series.OnAirToday
import com.example.corngrain.data.network.response.series.PopularSeries
import com.example.corngrain.data.network.response.series.SerieDetail

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

    //Playing
    val playingMovies : LiveData<Playing>
    suspend fun loadPlayingMovies()

    //SERIES => ON AIR TODAY
    val onAirToday : LiveData<OnAirToday>
    suspend fun loadOnAirToday()

    //SERIES => POPULAR
    val popularSeries:LiveData<PopularSeries>
    suspend fun loadPopularSeries()

    val serieDetail:LiveData<SerieDetail>
    suspend fun loadSerieDetail(id:Int)
}