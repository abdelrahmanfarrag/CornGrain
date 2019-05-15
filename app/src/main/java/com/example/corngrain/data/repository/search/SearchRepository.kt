package com.example.corngrain.data.repository.search

import android.graphics.Movie
import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.search.MovieSearch

interface SearchRepository {

    suspend fun loadSearchedMovieList(query:String,page:Int):LiveData<MovieSearch>
}