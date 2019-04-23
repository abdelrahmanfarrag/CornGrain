package com.example.corngrain.ui.main.series

import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.utilities.lazyDeferred

class SeriesViewModel(repository: SeriesRepository) : ViewModel() {

    val fetchSeries by lazyDeferred {
        repository.getOnAirTodaySeries()
    }
    val fetchPopularSeries by lazyDeferred {
        repository.getPopularSeries()
    }
}
