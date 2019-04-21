package com.example.corngrain.data.repository.di

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.LatestMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TmdbRepositoryImpl(private val networkSource: TmdbNetworkLayer) : TmdbRepository {

    override suspend fun getLatestMovies() {
         withContext(Dispatchers.IO) {
             networkSource.loadLatestMovies()
        }
    }

}
