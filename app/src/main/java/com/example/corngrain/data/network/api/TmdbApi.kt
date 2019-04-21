package com.example.corngrain.data.network.api

import com.example.corngrain.data.network.di.LogginInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.response.LatestMovies
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

//ENDPOINTS
const val LATEST_MOVIES = "movie/latest"

//LatestMovies =>https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

interface TmdbApi {

    @GET(LATEST_MOVIES)
    fun getLatestMovies(@Query(LANGUAGE) language: String = "en-Us")
            : Deferred<LatestMovies>


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