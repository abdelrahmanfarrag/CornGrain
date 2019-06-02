package com.example.corngrain.ui.main.trending

import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.repository.trending.TrendingRepository
import com.example.corngrain.utilities.lazyDeferred

class TrendingViewModel(private val repository: TrendingRepository) : ViewModel() {

    val fetchTrendingMovies by lazyDeferred {
        repository.loadTrendingMovies()
    }
    val fetchTrendingSeries by lazyDeferred {
        repository.loadTrendingSeries()
    }

}
