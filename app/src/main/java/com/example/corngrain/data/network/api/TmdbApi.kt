package com.example.corngrain.data.network.api

import com.example.corngrain.data.network.di.LogginInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.response.Popular
import com.example.corngrain.data.network.response.Upcoming
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Queries & Parameters
const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "f696be1437ab553903ebf8ff5203da2e"
const val KEY = "api_key"
const val LANGUAGE = "language"
const val PAGE = "page"

//ENDPOINTS
const val POPULAR_MOVIES = "movie/popular"
const val UPCOMING_MOVIES = "movie/upcoming"

//LatestMovies =>https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

interface TmdbApi {

    @GET(POPULAR_MOVIES)
    fun getLatestMoviesAsync(
        @Query(LANGUAGE) language: String = "en-Us",
        @Query(PAGE) page: Int = 1
    )
            : Deferred<Popular>

    @GET(UPCOMING_MOVIES)
    fun getUpcomingMoviesAsync(
        @Query(LANGUAGE) language: String = "en-US"
        , @Query(PAGE) page: Int = 1
    )
            : Deferred<Upcoming>

    companion object {

        operator fun invoke(
            noConnectionInterceptor: NoConnectionInterceptor,
            logginInterceptor: LogginInterceptor
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
                .addInterceptor(logginInterceptor.loggingInterceptor())
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