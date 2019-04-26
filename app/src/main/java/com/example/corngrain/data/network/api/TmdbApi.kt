package com.example.corngrain.data.network.api

import com.example.corngrain.data.network.di.LoggingInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.response.movies.Playing
import com.example.corngrain.data.network.response.movies.Popular
import com.example.corngrain.data.network.response.movies.TopRated
import com.example.corngrain.data.network.response.movies.Upcoming
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.data.network.response.series.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

//ENDPOINTS => For Series
const val ONAIR_TODAY = "tv/airing_today"
const val POPULAR_SERIES = "tv/popular"
const val TOP_RATED_SERIES = "tv/top_rated"
const val CURRENTLY_SHOWING = "tv/on_the_air"
const val SERIE_DETAIL = "tv/{id}"

//ENDPOINTS => For Persons
const val POPULAR_PERSONS = "person/popular"

//LatestMovies =>https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

interface TmdbApi {

    @GET(POPULAR_MOVIES)
    fun getLatestMoviesAsync(
        @Query(LANGUAGE) language: String = "en-Us",
        @Query(PAGE) page: Int = 1
    ): Deferred<Popular>

    @GET(UPCOMING_MOVIES)
    fun getUpcomingMoviesAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    ): Deferred<Upcoming>

    @GET(TOPRATED_MOVIES)
    fun getTopRatedMoviesAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    ): Deferred<TopRated>

    @GET(PLAYING_MOVIES)
    fun getPlayingMoviesAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    ): Deferred<Playing>

    @GET(ONAIR_TODAY)
    fun getTvOnAirTodayAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    ): Deferred<OnAirToday>

    @GET(POPULAR_SERIES)
    fun getTvPopularSeriesAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
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
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    ): Deferred<SerieCurrentlyShowing>


    @GET(POPULAR_PERSONS)
    fun getPopularPersonsAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1

    ):
            Deferred<PopularPersons>

    companion object {

        operator fun invoke(
            noConnectionInterceptor: NoConnectionInterceptor,
            loggingInterceptor: LoggingInterceptor
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

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptedUrl)
                .addInterceptor(loggingInterceptor.loggingInterceptor())
                .addInterceptor(noConnectionInterceptor)
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