package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModel
import com.example.corngrain.data.repository.movies.TmdbRepository
import com.example.corngrain.utilities.lazyDeferred

class MoviesViewModel(private val repository: TmdbRepository) : ViewModel() {
    val fetchLatestMovies by lazyDeferred {
        repository.getPopularMovies()
    }
    val fetchUpcomingMovies by lazyDeferred {
        repository.getUpcomingMovies()
    }
    val fetchTopRatedMovies by lazyDeferred {
        repository.getTopRatedMovies()
    }
    val fetchPlayingMovies by lazyDeferred {
        repository.getPlayingMovies()
    }

}
