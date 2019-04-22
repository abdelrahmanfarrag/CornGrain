package com.example.corngrain.data.repository.di

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.PopularEntity

interface TmdbRepository {

    suspend fun getPopularMovies(): List<PopularEntity>
}