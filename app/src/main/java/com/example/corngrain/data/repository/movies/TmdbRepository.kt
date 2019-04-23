package com.example.corngrain.data.repository.movies

import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity

interface TmdbRepository {

    suspend fun getPopularMovies(): List<PopularEntity>

    suspend fun getUpcomingMovies(): List<UpcomingEntity>

    suspend fun getTopRatedMovies(): List<TopRatedEntity>

    suspend fun getPlayingMovies(): List<PlayingEntity>
}