package com.example.corngrain.data.repository.di

import com.example.corngrain.data.db.entity.PlayingEntity
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.data.db.entity.TopRatedEntity
import com.example.corngrain.data.db.entity.UpcomingEntity

interface TmdbRepository {

    suspend fun getPopularMovies(): List<PopularEntity>

    suspend fun getUpcomingMovies(): List<UpcomingEntity>

    suspend fun getTopRatedMovies(): List<TopRatedEntity>

    suspend fun getPlayingMovies(): List<PlayingEntity>
}