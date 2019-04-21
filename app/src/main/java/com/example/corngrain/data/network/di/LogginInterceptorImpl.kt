package com.example.corngrain.data.network.di

import okhttp3.logging.HttpLoggingInterceptor

class LogginInterceptorImpl : LogginInterceptor {


    override fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}