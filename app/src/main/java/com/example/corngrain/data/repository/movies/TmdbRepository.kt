package com.example.corngrain.data.repository.movies

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.*
import com.example.corngrain.data.network.response.movies.*

interface TmdbRepository {

    suspend fun getPlayingMoviesFromResponse(page:Int): LiveData<PlayingMovies>

    suspend fun getUpcomingMovies(page: Int): LiveData<UpcomingMovies>

    suspend fun getTopRatedMovies(page: Int): LiveData<TopRatedMovies>

    suspend fun getPopularMovies(page: Int): LiveData<PopularMovies>

    suspend fun getDetailedMovie(id:Int):LiveData<Detail>

    suspend fun getMovieCast(id:Int):LiveData<Credits>

    suspend fun getReviews(id:Int):LiveData<Reviews>

    suspend fun getTrailers(id: Int):LiveData<Videos>

    suspend fun getSimilarMovies(id:Int):LiveData<Similar>

}