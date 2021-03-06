package com.example.corngrain.ui.main.movies

import android.util.Log
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

    val ratedMovies by lazyDeferred {
        repository.getTopRatedMovies(1)
    }
    val playingMovies by lazyDeferred {
        repository.getPlayingMoviesFromResponse(1)
    }
    val upcomingMovies by lazyDeferred {
        repository.getUpcomingMovies(1)
    }
    val popularMovies by lazyDeferred {
        repository.getPopularMovies(1)
    }

    suspend fun loadMoreRatedMoviesAsync(page: Int): LiveData<TopRatedMovies> {
        val fetchMoreRatedMovies by lazyDeferred {
            repository.getTopRatedMovies(page)
        }
        return fetchMoreRatedMovies.await()
    }

    suspend fun loadMorePopularMoviesAsync(page: Int): LiveData<PopularMovies> {
        val fetchPopularMovies by lazyDeferred {
            repository.getPopularMovies(page)
        }
        return fetchPopularMovies.await()
    }

    suspend fun loadMorePlayingMoviesAsync(page: Int): LiveData<PlayingMovies> {
        val fetchMoreMovies by lazyDeferred {
            repository.getPlayingMoviesFromResponse(page)
        }
        return fetchMoreMovies.await()
    }

    suspend fun loadMoreUpcomingMoviesAsync(page: Int): LiveData<UpcomingMovies> {
        val fetchMoreUpcomingMovies by lazyDeferred {
            repository.getUpcomingMovies(page)
        }
        return fetchMoreUpcomingMovies.await()
    }


}
