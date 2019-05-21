package com.example.corngrain.data.network.outsource

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.*
import com.example.corngrain.data.network.response.movies.*
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending

interface TmdbNetworkLayer {
    //PopularPersons Movies
    val popularMovies: LiveData<PopularMovies>

    suspend fun loadPopularMovies(page: Int)

    //Upcoming
    val upcomingMovies: LiveData<UpcomingMovies>

    suspend fun loadUpcomingMovies(page: Int)

    //TopRated
    val topRatedMovies: LiveData<TopRatedMovies>

    suspend fun loadTopRatedMovies(page: Int)

    //Playing
    val playingMovies: LiveData<PlayingMovies>


    //Detail
    val movieDetail: LiveData<Detail>

    suspend fun loadMovieDetail(id: Int)


    suspend fun loadPlayingMovies(page: Int)
    //Cast
    val cast: LiveData<Credits>

    suspend fun loadMovieCast(id: Int)

    //Reviews
    val reviews: LiveData<Reviews>

    suspend fun loadReviews(id: Int)

    //Videos
    val videos: LiveData<Videos>

    suspend fun loadTrailers(id: Int)

    //Similar
    val similar: LiveData<Similar>

    suspend fun loadSimilarMovies(id: Int)

    //SERIES => ON AIR TODAY
    val onAirToday: LiveData<OnAirToday>

    suspend fun loadOnAirToday(page: Int)

    //SERIES => POPULAR
    val popularSeries: LiveData<PopularSeries>

    suspend fun loadPopularSeries(page: Int)

    val serieDetail: LiveData<SerieDetail>
    suspend fun loadSerieDetail(id: Int)

    val topRatedSeries: LiveData<TopRatedSeries>
    suspend fun loadTopRatedSeries(page: Int)

    val currentlyViewingSeries: LiveData<SerieCurrentlyShowing>
    suspend fun loadInshowSeries(page: Int)

    val serieCredits:LiveData<Credits>
    suspend fun loadSerieCredits(id:Int)

    val serieReviews:LiveData<Videos>
    suspend fun loadSerieReviews(id:Int)


    //PERSONS
    val popularPersons: LiveData<PopularPersons>

    suspend fun loadPopularPersons()

    val personDetail: LiveData<PersonDetail>
    suspend fun loadPersonDetail(id: Int)

    val personMovies: LiveData<PersonMovies>
    suspend fun loadPersonMovies(id: Int)


    //Search
    val searchMovies: LiveData<MovieSearch>

    suspend fun getUserSearchedMovies(query: String, page: Int)

    //trending
    val trending: LiveData<Trending>

    suspend fun getTrendingMovies()

    val trendingSeries: LiveData<SeriesAndTvShows>

    suspend fun getTrendingShows()
}