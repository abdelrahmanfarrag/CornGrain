package com.example.corngrain.ui.main.movies.details

import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.repository.movies.TmdbRepository
import com.example.corngrain.utilities.lazyDeferred

class MovieDetailViewModel(id: Int, repository: TmdbRepository) : ViewModel() {

    val fetchMovieDetail by lazyDeferred {
        repository.getDetailedMovie(id)
    }

    val fetchMovieCast by lazyDeferred {
        repository.getMovieCast(id)
    }

    val fetchMovieReviews by lazyDeferred {
        repository.getReviews(id)
    }
}
