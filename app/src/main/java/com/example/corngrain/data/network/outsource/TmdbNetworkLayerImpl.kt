package com.example.corngrain.data.network.outsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.response.*
import com.example.corngrain.data.network.response.movies.*
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending
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
    private val _mutableMovieDetail = MutableLiveData<Detail>()
    private val _mutableMovieCast = MutableLiveData<Credits>()
    private val _mutableReview = MutableLiveData<Reviews>()
    private val _mutableVideo = MutableLiveData<Videos>()
    private val _mutableSimilar = MutableLiveData<Similar>()
    private val _mutableSearchedMovies = MutableLiveData<MovieSearch>()
    private val _mutableTrendingMovies = MutableLiveData<Trending>()
    private val _mutableTrendingShows = MutableLiveData<SeriesAndTvShows>()
    private val _mutableSerieCrdit = MutableLiveData<Credits>()
    private val _mutableSerieReviews = MutableLiveData<Videos>()
    private val _mutableSerieSeason = MutableLiveData<Season>()


    override val popularMovies: LiveData<PopularMovies>
        get() = _mutablePopularMovies

    override suspend fun loadPopularMovies(page: Int) {
        try {
            val latestMoviesJob = api.getPopularMoviesAsync(page).await()
            _mutablePopularMovies.postValue(latestMoviesJob)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")
        }
    }

    override val upcomingMovies: LiveData<UpcomingMovies>
        get() = _mutableUpcomingMoviesData

    override suspend fun loadUpcomingMovies(page: Int) {
        try {
            val upcomingJob = api.getUpcomingMoviesAsync(page).await()
            _mutableUpcomingMoviesData.postValue(upcomingJob)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")
        }
    }

    override val topRatedMovies: LiveData<TopRatedMovies>
        get() = _mutableTopRatedMoviesData

    override suspend fun loadTopRatedMovies(page: Int) {
        try {
            val topRatedJob = api.getTopRatedMoviesAsync(page).await()
            _mutableTopRatedMoviesData.postValue(topRatedJob)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")
        }
    }

    override val playingMovies: LiveData<PlayingMovies>
        get() = _mutablePlayingMoviesData

    override suspend fun loadPlayingMovies(page: Int) {
        try {
            val playingJob = api.getPlayingMoviesAsync(page).await()
            _mutablePlayingMoviesData.postValue(playingJob)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")
        }
    }

    override val onAirToday: LiveData<OnAirToday>
        get() = _mutableOnAirSeriesData

    override suspend fun loadOnAirToday(page: Int) {
        try {
            val onAirData = api.getTvOnAirTodayAsync(page).await()
            _mutableOnAirSeriesData.postValue(onAirData)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val popularSeries: LiveData<PopularSeries>
        get() = _mutablePopularSeriesData

    override suspend fun loadPopularSeries(page: Int) {
        try {
            val popularSeriesData = api.getTvPopularSeriesAsync(page).await()
            _mutablePopularSeriesData.postValue(popularSeriesData)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val serieDetail: LiveData<SerieDetail>
        get() = _mutableSerieDetail

    override suspend fun loadSerieDetail(id: Int) {
        try {
            val serieDetail = api.getSerieDetailAsync(id).await()
            _mutableSerieDetail.postValue(serieDetail)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val topRatedSeries: LiveData<TopRatedSeries>
        get() = _mutableTopRatedSeries

    override suspend fun loadTopRatedSeries(page: Int) {
        try {
            val topRatedSeries = api.getTopRatedSeriesAsync(page).await()
            _mutableTopRatedSeries.postValue(topRatedSeries)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }


    override val currentlyViewingSeries: LiveData<SerieCurrentlyShowing>
        get() = _mutableInshowSeries

    override suspend fun loadInshowSeries(page: Int) {
        try {
            val inshowSeries = api.getCurrentlyShowingSeriesAsync(page).await()
            _mutableInshowSeries.postValue(inshowSeries)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override suspend fun loadPopularPersons() {
        try {
            val popularPersons = api.getPopularPersonsAsync().await()
            _mutablePopularPersons.postValue(popularPersons)

        } catch (e: Throwable) {
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


        } catch (e: Throwable) {
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

    override val similar: LiveData<Similar>
        get() = _mutableSimilar

    override suspend fun loadSimilarMovies(id: Int) {
        try {
            val similar = api.getSimilarMoviesAsync(id).await()
            _mutableSimilar.postValue(similar)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val videos: LiveData<Videos>
        get() = _mutableVideo

    override suspend fun loadTrailers(id: Int) {
        try {
            val videos = api.getMovieTrailersAsync(id).await()
            _mutableVideo.postValue(videos)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val reviews: LiveData<Reviews>
        get() = _mutableReview

    override suspend fun loadReviews(id: Int) {
        try {
            val reviews = api.getReviewsAsync(id).await()
            _mutableReview.postValue(reviews)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }


    override val cast: LiveData<Credits>
        get() = _mutableMovieCast

    override suspend fun loadMovieCast(id: Int) {
        try {
            val cast = api.getMovieCastAsync(id).await()
            _mutableMovieCast.postValue(cast)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val movieDetail: LiveData<Detail>
        get() = _mutableMovieDetail

    override suspend fun loadMovieDetail(id: Int) {
        try {
            val movieDetail = api.getDetailOfMovieAsync(id).await()
            _mutableMovieDetail.postValue(movieDetail)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val searchMovies: LiveData<MovieSearch>
        get() = _mutableSearchedMovies

    override suspend fun getUserSearchedMovies(query: String, page: Int) {
        try {
            val loadSeachMoviesJob = api.searchForaMovieAsync(query, page).await()
            _mutableSearchedMovies.postValue(loadSeachMoviesJob)
        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val trending: LiveData<Trending>
        get() = _mutableTrendingMovies

    override suspend fun getTrendingMovies() {
        try {
            val loadTrendingJob = api.trendingMoviesAsync().await()
            _mutableTrendingMovies.postValue(loadTrendingJob)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val trendingSeries: LiveData<SeriesAndTvShows>
        get() = _mutableTrendingShows


    override suspend fun getTrendingShows() {
        try {
            val loadTrendingJob = api.trendingTvShowsAsync().await()
            _mutableTrendingShows.postValue(loadTrendingJob)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }

    }

    override val serieCredits: LiveData<Credits>
        get() = _mutableSerieCrdit

    override suspend fun loadSerieCredits(id: Int) {
        try {
            val loadSerieCast = api.getSerieCastAsync(id).await()
            _mutableSerieCrdit.postValue(loadSerieCast)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val serieReviews: LiveData<Videos>
        get() = _mutableSerieReviews

    override suspend fun loadSerieReviews(id: Int) {
        try {
            val loadSerieCast = api.getSerieReviewsAsync(id).await()
            _mutableSerieReviews.postValue(loadSerieCast)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

    override val serieSeason: LiveData<Season>
        get() = _mutableSerieSeason

    override suspend fun laodSerieSeasons(id: Int, seasonNumber: Int) {
        try {
            val season = api.getSeriesSeasonsAsync(id, seasonNumber).await()
            _mutableSerieSeason.postValue(season)

        } catch (e: Throwable) {
            Log.d("noConnection", "No network")

        }
    }

}