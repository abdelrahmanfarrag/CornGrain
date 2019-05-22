package com.example.corngrain.data.network.api

import android.content.Context
import android.net.ConnectivityManager
import com.example.corngrain.data.network.di.LoggingInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.response.*
import com.example.corngrain.data.network.response.movies.*
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//Queries & Parameters
const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "f696be1437ab553903ebf8ff5203da2e"
const val KEY = "api_key"
const val LANGUAGE = "language"
const val PAGE = "page"

//ENDPOINTS => For movies
const val POPULAR_MOVIES = "movie/popular"
const val UPCOMING_MOVIES = "movie/upcoming"
const val TOPRATED_MOVIES = "movie/top_rated"
const val PLAYING_MOVIES = "movie/now_playing"
const val MOVIE_DETAIL = "movie/{id}"
const val MOVIE_CAST = "movie/{id}/credits"
const val REVIEWS = "movie/{id}/reviews"
const val VIDEOS = "movie/{id}/videos"
const val SIMILAR = "movie/{id}/similar"


//ENDPOINTS => For Series
const val ONAIR_TODAY = "tv/airing_today"
const val POPULAR_SERIES = "tv/popular"
const val TOP_RATED_SERIES = "tv/top_rated"
const val CURRENTLY_SHOWING = "tv/on_the_air"
const val SERIE_CAST = "tv/{id}/credits"
const val TV_REVIEWS = "tv/{id}/videos"
const val SERIE_DETAIL = "tv/{id}"

//ENDPOINTS => For Persons
const val POPULAR_PERSONS = "person/popular"
const val POPULAR_DETAIL = "person/{id}"
const val PERSON_MOVIES = "person/{id}/combined_credits"

//SEARCH APIS
const val MOVIE_SEARCH = "search/movie"


//TRENDING API
const val TRENDING_MOVIE = "trending/movie/day"
const val TRENDING_SERIES_TV_SHOWS = "trending/tv/day"
const val TRENDING_SUPER_STARS = "trending/tv/day"

//LatestMovies =>https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

interface TmdbApi {

    @GET(POPULAR_MOVIES)
    fun getPopularMoviesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<PopularMovies>

    @GET(UPCOMING_MOVIES)
    fun getUpcomingMoviesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<UpcomingMovies>

    @GET(TOPRATED_MOVIES)
    fun getTopRatedMoviesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<TopRatedMovies>

    @GET(PLAYING_MOVIES)
    fun getPlayingMoviesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<PlayingMovies>

    @GET(ONAIR_TODAY)
    fun getTvOnAirTodayAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<OnAirToday>

    @GET(POPULAR_SERIES)
    fun getTvPopularSeriesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<PopularSeries>

    @GET(SERIE_DETAIL)
    fun getSerieDetailAsync(
        @Path("id") serieId: Int
    ): Deferred<SerieDetail>

    @GET(TOP_RATED_SERIES)
    fun getTopRatedSeriesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<TopRatedSeries>

    @GET(CURRENTLY_SHOWING)
    fun getCurrentlyShowingSeriesAsync(
        @Query(PAGE) page: Int = 1
    ): Deferred<SerieCurrentlyShowing>


    @GET(POPULAR_PERSONS)
    fun getPopularPersonsAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    ): Deferred<PopularPersons>

    @GET(POPULAR_DETAIL)
    fun getPopularPersonDetailAsync(
        @Path("id") personId: Int
    ): Deferred<PersonDetail>

    @GET(PERSON_MOVIES)
    fun getPersonMoviesAsync(
        @Path("id") personId: Int
    ): Deferred<PersonMovies>

    @GET(MOVIE_DETAIL)
    fun getDetailOfMovieAsync(
        @Path("id") movieId: Int
    ): Deferred<Detail>

    @GET(MOVIE_CAST)
    fun getMovieCastAsync(
        @Path("id") movieId: Int
    ): Deferred<Credits>

    @GET(REVIEWS)
    fun getReviewsAsync(
        @Path("id") mediaId: Int
    ): Deferred<Reviews>

    @GET(VIDEOS)
    fun getMovieTrailersAsync(
        @Path("id") mediaId: Int
    ): Deferred<Videos>

    @GET(SIMILAR)
    fun getSimilarMoviesAsync(
        @Path("id") mediaId: Int
    ): Deferred<Similar>

    @GET(MOVIE_SEARCH)
    fun searchForaMovieAsync(
        @Query("query") query: String,
        @Query("page") page: Int
        , @Query("include_adult") adult: Boolean = false
    ): Deferred<MovieSearch>

    @GET(TRENDING_MOVIE)
    fun trendingMoviesAsync(
    ): Deferred<Trending>

    @GET(TRENDING_SERIES_TV_SHOWS)
    fun trendingTvShowsAsync()
            : Deferred<SeriesAndTvShows>

    @GET(SERIE_CAST)
    fun getSerieCastAsync(@Path("id") id: Int)
            : Deferred<Credits>

    @GET(TV_REVIEWS)
    fun getSerieReviewsAsync(@Path("id") id: Int)
            : Deferred<Videos>


    companion object {
        private fun isOnline(appContext: Context): Boolean {
            val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected

        }


        operator fun invoke(
            noConnectionInterceptor: NoConnectionInterceptor,
            loggingInterceptor: LoggingInterceptor,
            context: Context
        ): TmdbApi {
            val interceptedUrl = Interceptor { chain ->
                val interceptedUrl = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(
                        KEY,
                        API_KEY
                    )
                    .build()

                val request = chain
                    .request()
                    .newBuilder()
                    .url(interceptedUrl)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val cacheSize = (5 * 1024 * 1024).toLong()
            val caching = Cache(context.cacheDir, cacheSize)
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptedUrl)
                .addInterceptor(loggingInterceptor.loggingInterceptor())
                .addInterceptor(noConnectionInterceptor)
                .cache(caching)
                .addInterceptor { chain ->
                    var requested = chain.request()
                    requested = if (isOnline(context))
                        requested.newBuilder().header(
                            "Cache-Control",
                            "public, max-age=" + 5
                        ).build()
                    else
                        requested.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        ).build()
                    chain.proceed(requested)
                }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            return Retrofit
                .Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbApi::class.java)


        }
    }

}