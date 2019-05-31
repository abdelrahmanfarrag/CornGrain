package com.example.corngrain.data.network.outsource

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
    private val _mutableSerieCredit = MutableLiveData<Credits>()
    private val _mutableSerieReviews = MutableLiveData<Videos>()
    private val _mutableSerieSeason = MutableLiveData<Season>()

    private fun <T> getServicesResponse(data: T, _mutableData: MutableLiveData<T>) {
        _mutableData.postValue(data)
    }

    override val popularMovies: LiveData<PopularMovies>
        get() = _mutablePopularMovies

    override suspend fun loadPopularMovies(page: Int) {
        getServicesResponse(api.getPopularMoviesAsync(page).await(), _mutablePopularMovies)
    }

    override val upcomingMovies: LiveData<UpcomingMovies>
        get() = _mutableUpcomingMoviesData

    override suspend fun loadUpcomingMovies(page: Int) {
        getServicesResponse(api.getUpcomingMoviesAsync(page).await(), _mutableUpcomingMoviesData)
    }

    override val topRatedMovies: LiveData<TopRatedMovies>
        get() = _mutableTopRatedMoviesData

    override suspend fun loadTopRatedMovies(page: Int) {
        getServicesResponse(api.getTopRatedMoviesAsync(page).await(), _mutableTopRatedMoviesData)
    }

    override val playingMovies: LiveData<PlayingMovies>
        get() = _mutablePlayingMoviesData

    override suspend fun loadPlayingMovies(page: Int) {
        getServicesResponse(api.getPlayingMoviesAsync(page).await(), _mutablePlayingMoviesData)
    }

    override val onAirToday: LiveData<OnAirToday>
        get() = _mutableOnAirSeriesData

    override suspend fun loadOnAirToday(page: Int) {
        getServicesResponse(api.getTvOnAirTodayAsync(page).await(), _mutableOnAirSeriesData)
    }

    override val popularSeries: LiveData<PopularSeries>
        get() = _mutablePopularSeriesData

    override suspend fun loadPopularSeries(page: Int) {
        getServicesResponse(api.getTvPopularSeriesAsync(page).await(), _mutablePopularSeriesData)
    }

    override val serieDetail: LiveData<SerieDetail>
        get() = _mutableSerieDetail

    override suspend fun loadSerieDetail(id: Int) {
        getServicesResponse(api.getSerieDetailAsync(id).await(), _mutableSerieDetail)
    }

    override val topRatedSeries: LiveData<TopRatedSeries>
        get() = _mutableTopRatedSeries

    override suspend fun loadTopRatedSeries(page: Int) {
        getServicesResponse(api.getTopRatedSeriesAsync(page).await(), _mutableTopRatedSeries)
    }

    override val currentlyViewingSeries: LiveData<SerieCurrentlyShowing>
        get() = _mutableInshowSeries

    override suspend fun loadInshowSeries(page: Int) {
        getServicesResponse(api.getCurrentlyShowingSeriesAsync(page).await(), _mutableInshowSeries)
    }

    override suspend fun loadPopularPersons() {
        getServicesResponse(api.getPopularPersonsAsync().await(), _mutablePopularPersons)
    }

    override val popularPersons: LiveData<PopularPersons>
        get() = _mutablePopularPersons


    override val personDetail: LiveData<PersonDetail>
        get() = _mutablePersonDetailData

    override suspend fun loadPersonDetail(id: Int) {
        getServicesResponse(api.getPopularPersonDetailAsync(id).await(), _mutablePersonDetailData)
    }

    override val personMovies: LiveData<PersonMovies>
        get() = _mutablePersonMoviesData

    override suspend fun loadPersonMovies(id: Int) {
        getServicesResponse(api.getPersonMoviesAsync(id).await(), _mutablePersonMoviesData)
    }

    override val similar: LiveData<Similar>
        get() = _mutableSimilar

    override suspend fun loadSimilarMovies(id: Int) {
        getServicesResponse(api.getSimilarMoviesAsync(id).await(), _mutableSimilar)
    }

    override val videos: LiveData<Videos>
        get() = _mutableVideo

    override suspend fun loadTrailers(id: Int) {
        getServicesResponse(api.getMovieTrailersAsync(id).await(), _mutableVideo)
    }

    override val reviews: LiveData<Reviews>
        get() = _mutableReview

    override suspend fun loadReviews(id: Int) {
        getServicesResponse(api.getReviewsAsync(id).await(), _mutableReview)
    }


    override val cast: LiveData<Credits>
        get() = _mutableMovieCast

    override suspend fun loadMovieCast(id: Int) {
        getServicesResponse(api.getMovieCastAsync(id).await(), _mutableMovieCast)
    }

    override val movieDetail: LiveData<Detail>
        get() = _mutableMovieDetail

    override suspend fun loadMovieDetail(id: Int) {
        getServicesResponse(api.getDetailOfMovieAsync(id).await(), _mutableMovieDetail)
    }

    override val searchMovies: LiveData<MovieSearch>
        get() = _mutableSearchedMovies

    override suspend fun getUserSearchedMovies(query: String, page: Int) {
        getServicesResponse(api.searchForaMovieAsync(query, page).await(), _mutableSearchedMovies)
    }

    override val trending: LiveData<Trending>
        get() = _mutableTrendingMovies

    override suspend fun getTrendingMovies() {
        getServicesResponse(api.trendingMoviesAsync().await(), _mutableTrendingMovies)
    }

    override val trendingSeries: LiveData<SeriesAndTvShows>
        get() = _mutableTrendingShows


    override suspend fun getTrendingShows() {
        getServicesResponse(api.trendingTvShowsAsync().await(), _mutableTrendingShows)
    }

    override val serieCredits: LiveData<Credits>
        get() = _mutableSerieCredit

    override suspend fun loadSerieCredits(id: Int) {
        getServicesResponse(api.getSerieCastAsync(id).await(), _mutableSerieCredit)
    }

    override val serieReviews: LiveData<Videos>
        get() = _mutableSerieReviews

    override suspend fun loadSerieReviews(id: Int) {
        getServicesResponse(api.getSerieReviewsAsync(id).await(), _mutableSerieReviews)
    }

    override val serieSeason: LiveData<Season>
        get() = _mutableSerieSeason

    override suspend fun laodSerieSeasons(id: Int, seasonNumber: Int) {
        getServicesResponse(
            api.getSeriesSeasonsAsync(id, seasonNumber).await(),
            _mutableSerieSeason
        )
    }

}