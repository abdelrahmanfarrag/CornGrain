package com.example.corngrain.data.network.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.corngrain.utilities.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response

class NoConnectionInterceptorImpl(context: Context) :
    NoConnectionInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) throw NoNetworkException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

    }
}