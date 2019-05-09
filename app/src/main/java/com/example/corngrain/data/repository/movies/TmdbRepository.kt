package com.example.corngrain.data.repository.movies

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.response.Detail
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.data.network.response.Similar
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.data.network.response.movies.*

interface TmdbRepository {

    suspend fun getPlayingMoviesFromResponse(page:Int): LiveData<PlayingMovies>

    suspend fun getUpcomingMovies(page: Int): LiveData<UpcomingMovies>

    suspend fun getTopRatedMovies(page: Int): LiveData<TopRatedMovies>

    suspend fun getPopularMovies(page: Int): LiveData<PopularMovies>

    suspend fun getDetailedMovie(id:Int):LiveData<Detail>

    suspend fun getMovieCast(id:Int):LiveData<MovieCredits>

    suspend fun getReviews(id:Int):LiveData<Reviews>

    suspend fun getTrailers(id: Int):LiveData<Videos>

    suspend fun getSimilarMovies(id:Int):LiveData<Similar>

}