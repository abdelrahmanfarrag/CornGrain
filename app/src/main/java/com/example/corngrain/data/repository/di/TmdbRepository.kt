package com.example.corngrain.data.repository.di

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.data.db.entity.UpcomingEntity

interface TmdbRepository {

    suspend fun getPopularMovies(): List<PopularEntity>

    suspend fun getUpcomingMovies():List<UpcomingEntity>
}