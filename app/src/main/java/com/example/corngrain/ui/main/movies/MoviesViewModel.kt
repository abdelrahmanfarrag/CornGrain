package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModel
import com.example.corngrain.data.repository.movies.TmdbRepository
import com.example.corngrain.utilities.lazyDeferred

class MoviesViewModel(private val repository: TmdbRepository) : ViewModel() {
    val fetchPlayingMovies by lazyDeferred {
        repository.getPlayingMoviesFromResponse()
    }
    val fetchUpcomingMovies by lazyDeferred {
        repository.getUpcomingMovies()
    }
    val fetchPopularMovies by lazyDeferred {
        repository.getPopularMovies()
    }
    val fetchTopRatedMovies by lazyDeferred {
        repository.getTopRatedMovies()
    }
 //   val fetchPlayingMovies by lazyDeferred {
   //     repository.getPopularMovies()
   // }

}
