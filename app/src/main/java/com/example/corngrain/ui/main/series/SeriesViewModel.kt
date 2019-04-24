package com.example.corngrain.ui.main.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.utilities.lazyDeferred
import kotlinx.coroutines.Deferred

class SeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    val fetchSeries by lazyDeferred {
        repository.getOnAirTodaySeries()
    }
    val fetchPopularSeries by lazyDeferred {
        repository.getPopularSeries()
    }

    fun fetchDetails(id: Int): Deferred<LiveData<SerieDetail>> {
        val fetchDetail by lazyDeferred {
            repository.getSerieDetail(id)
        }
        return fetchDetail
    }


}
