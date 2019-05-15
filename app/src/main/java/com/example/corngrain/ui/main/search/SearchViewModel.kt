package com.example.corngrain.ui.main.search

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.data.repository.search.SearchRepository
import com.example.corngrain.utilities.lazyDeferred

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    suspend fun getSearchedMoviesAsync(query:String,page:Int):LiveData<MovieSearch>{
        val fetchSearchMovies by lazyDeferred {
            searchRepository.loadSearchedMovieList(query,page)
        }
        return fetchSearchMovies.await()
    }
}
