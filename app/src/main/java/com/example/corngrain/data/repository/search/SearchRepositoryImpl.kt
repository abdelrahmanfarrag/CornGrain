package com.example.corngrain.data.repository.search

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.search.MovieSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(private val networkSource: TmdbNetworkLayer) : SearchRepository {
    override suspend fun loadSearchedMovieList(query: String, page: Int): LiveData<MovieSearch> {
        return withContext(Dispatchers.IO) {
            networkSource.getUserSearchedMovies(query, page)
            return@withContext networkSource.searchMovies
        }
    }
}