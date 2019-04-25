package com.example.corngrain.ui.main.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.utilities.lazyDeferred

class SeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    val fetchSeries by lazyDeferred {
        repository.getOnAirTodaySeries()
    }
    val fetchPopularSeries by lazyDeferred {
        repository.getPopularSeries()
    }

    val fetchTopRatedSeries by lazyDeferred {
        repository.getRatedSeries()
    }

    val fetchInViewSeries by lazyDeferred {
        repository.getInshowSeries()
    }

    suspend fun fetchDetails(id: Int): LiveData<SerieDetail> {
        val fetchDetail by lazyDeferred {
            repository.getSerieDetail(id)
        }
        return fetchDetail.await()
    }


}
