package com.example.corngrain.data.repository.di

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.LatestMovies

interface TmdbRepository {
    //Current Movies
    suspend fun getLatestMovies()
}