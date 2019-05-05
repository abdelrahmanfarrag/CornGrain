package com.example.corngrain.data.network.outsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.response.movies.*
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.utilities.NoNetworkException

class TmdbNetworkLayerImpl(private val api: TmdbApi) : TmdbNetworkLayer {

    private val _mutablePopularMovies = MutableLiveData<PopularMovies>()
    private val _mutableUpcomingMoviesData = MutableLiveData<UpcomingMovies>()
    private val _mutableTopRatedMoviesData = MutableLiveData<TopRatedMovies>()
    private val _mutablePlayingMoviesData = MutableLiveData<PlayingMovies>()
    private val _mutableOnAirSeriesData = MutableLiveData<OnAirToday>()
    private val _mutablePopularSeriesData = MutableLiveData<PopularSeries>()
    private val _mutableSerieDetail = MutableLiveData<SerieDetail>()
    private val _mutableTopRatedSeries = MutableLiveData<TopRatedSeries>()
    private val _mutableInshowSeries = MutableLiveData<SerieCurrentlyShowing>()
    private val _mutablePopularPersons = MutableLiveData<PopularPersons>()
    private val _mutablePersonDetailData = MutableLiveData<PersonDetail>()
    private val _mutablePersonMoviesData = MutableLiveData<PersonMovies>()

    override val popularMovies: LiveData<PopularMovies>
        get() = _mutablePopularMovies

    override suspend fun loadPopularMovies() {
        try {
            val latestMoviesJob = api.getPopularMoviesAsync().await()
            _mutablePopularMovies.postValue(latestMoviesJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }

    override val upcomingMovies: LiveData<UpcomingMovies>
        get() = _mutableUpcomingMoviesData

    override suspend fun loadUpcomingMovies() {
        try {
            val upcomingJob = api.getUpcomingMoviesAsync().await()
            _mutableUpcomingMoviesData.postValue(upcomingJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val topRatedMovies: LiveData<TopRatedMovies>
        get() = _mutableTopRatedMoviesData

    override suspend fun loadTopRatedMovies() {
        try {
            val topRatedJob = api.getTopRatedMoviesAsync().await()
            _mutableTopRatedMoviesData.postValue(topRatedJob)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val playingMovies: LiveData<PlayingMovies>
        get() = _mutablePlayingMoviesData

    override suspend fun loadPlayingMovies() {
        try {
            val playingJob = api.getPlayingMoviesAsync().await()
            _mutablePlayingMoviesData.postValue(playingJob)

        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val onAirToday: LiveData<OnAirToday>
        get() = _mutableOnAirSeriesData

    override suspend fun loadOnAirToday() {
        try {
            val onAirData = api.getTvOnAirTodayAsync().await()
            _mutableOnAirSeriesData.postValue(onAirData)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }

    override val popularSeries: LiveData<PopularSeries>
        get() = _mutablePopularSeriesData

    override suspend fun loadPopularSeries() {
        try {
            val popularSeriesData = api.getTvPopularSeriesAsync().await()
            _mutablePopularSeriesData.postValue(popularSeriesData)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")
        }
    }

    override val serieDetail: LiveData<SerieDetail>
        get() = _mutableSerieDetail

    override suspend fun loadSerieDetail(id: Int) {
        try {
            val serieDetail = api.getSerieDetailAsync(id).await()
            _mutableSerieDetail.postValue(serieDetail)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val topRatedSeries: LiveData<TopRatedSeries>
        get() = _mutableTopRatedSeries

    override suspend fun loadTopRatedSeries() {
        try {
            val topRatedSeries = api.getTopRatedSeriesAsync().await()
            _mutableTopRatedSeries.postValue(topRatedSeries)
        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }


    override val currentlyViewingSeries: LiveData<SerieCurrentlyShowing>
        get() = _mutableInshowSeries

    override suspend fun loadInshowSeries() {
        try {
            val inshowSeries = api.getCurrentlyShowingSeriesAsync().await()
            _mutableInshowSeries.postValue(inshowSeries)

        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override suspend fun loadPopularPersons() {
        try {
            val popularPersons = api.getPopularPersonsAsync().await()
            _mutablePopularPersons.postValue(popularPersons)

        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val popularPersons: LiveData<PopularPersons>
        get() = _mutablePopularPersons


    override val personDetail: LiveData<PersonDetail>
        get() = _mutablePersonDetailData

    override suspend fun loadPersonDetail(id: Int) {
        try {
            val detail = api.getPopularPersonDetailAsync(id).await()
            _mutablePersonDetailData.postValue(detail)


        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }

    override val personMovies: LiveData<PersonMovies>
        get() = _mutablePersonMoviesData

    override suspend fun loadPersonMovies(id: Int) {
        try {
            val movies = api.getPersonMoviesAsync(id).await()
            _mutablePersonMoviesData.postValue(movies)

        } catch (e: NoNetworkException) {
            Log.d("noConnection", "No network")

        }
    }


}