package com.example.corngrain.data.repository.movies

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.response.movies.Playing
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.data.network.response.movies.UpcomingMovies

interface TmdbRepository {

    suspend fun getPopularMovies(): LiveData<PlayingMovies>

    suspend fun getUpcomingMovies(): LiveData<UpcomingMovies>

    suspend fun getTopRatedMovies(): List<TopRatedEntity>

    suspend fun getPlayingMovies(): List<PlayingEntity>
}