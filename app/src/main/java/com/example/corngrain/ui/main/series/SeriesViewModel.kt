package com.example.corngrain.ui.main.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.utilities.lazyDeferred
import com.example.corngrain.utilities.lazyDeferredWithId

class SeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    val fetchOnAirToday by lazyDeferred {
        repository.getOnAirTodaySeries(1)
    }
    val fetchPopularSeries by lazyDeferred {
        repository.getPopularSeries(1)
    }

    val fetchTopRatedSeries by lazyDeferred {
        repository.getRatedSeries(1)
    }

    val fetchInViewSeries by lazyDeferred {
        repository.getInshowSeries(1)
    }

    suspend fun loadOnAirToday(page: Int): LiveData<OnAirToday> {
        val fetchSeries by lazyDeferred {
            repository.getOnAirTodaySeries(page)
        }
        return fetchSeries.await()

    }

    suspend fun loadPopularSeries(page: Int): LiveData<PopularSeries> {
        val fetchPopularSeries by lazyDeferred {
            repository.getPopularSeries(page)
        }
        return fetchPopularSeries.await()
    }

    suspend fun loadTopRatedSeries(page: Int): LiveData<TopRatedSeries> {
        val fetchTopRatedSeries by lazyDeferred {
            repository.getRatedSeries(page)
        }
        return fetchTopRatedSeries.await()
    }

    suspend fun loadInViewSeries(page: Int): LiveData<SerieCurrentlyShowing> {
        val fetchCurrentlyInshow by lazyDeferred {
            repository.getInshowSeries(page)
        }
        return fetchCurrentlyInshow.await()
    }

    suspend fun fetchDetails(id: Int): LiveData<SerieDetail> {
        val fetchDetail by lazyDeferred {
            repository.getSerieDetail(id)
        }
        return fetchDetail.await()
    }

    suspend fun fetchCredits(id: Int): LiveData<Credits> {
        val fetchCast by lazyDeferred {
            repository.getSerieCast(id)
        }
        return fetchCast.await()
    }

    suspend fun fetchReviews(id: Int): LiveData<Videos> {
        val reviews by lazyDeferred {
            repository.getSerieReviews(id)
        }
        return reviews.await()
    }

}
