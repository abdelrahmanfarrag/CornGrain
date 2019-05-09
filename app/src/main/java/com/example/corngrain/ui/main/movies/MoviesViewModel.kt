package com.example.corngrain.ui.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.data.network.response.movies.PopularMovies
import com.example.corngrain.data.network.response.movies.TopRatedMovies
import com.example.corngrain.data.network.response.movies.UpcomingMovies
import com.example.corngrain.data.repository.movies.TmdbRepository
import com.example.corngrain.utilities.lazyDeferred
import kotlinx.coroutines.Deferred

class MoviesViewModel(private val repository: TmdbRepository) : ViewModel() {

    suspend fun loadMoreRatedMovies(page: Int):Deferred<LiveData<TopRatedMovies>>{
        val fetchMoreRatedMovies by lazyDeferred {
            repository.getTopRatedMovies(page)
        }
        return fetchMoreRatedMovies
    }

    suspend fun loadMorePopularMoviesAsync(page: Int): Deferred<LiveData<PopularMovies>> {
        val fetchPopularMovies by lazyDeferred {
            repository.getPopularMovies(page)
        }
        return fetchPopularMovies
    }

    suspend fun loadMorePlayingMoviesAsync(page: Int): Deferred<LiveData<PlayingMovies>> {
        val fetchMoreMovies by lazyDeferred {
            repository.getPlayingMoviesFromResponse(page)
        }
        return fetchMoreMovies
    }

    suspend fun loadMoreUpcomingMoviesAsync(page: Int): Deferred<LiveData<UpcomingMovies>> {
        val fetchMoreUpcomingMovies by lazyDeferred {
            repository.getUpcomingMovies(page)
        }
        return fetchMoreUpcomingMovies
    }

    //   val fetchPlayingMovies by lazyDeferred {
    //     repository.getPopularMovies()
    // }

}
