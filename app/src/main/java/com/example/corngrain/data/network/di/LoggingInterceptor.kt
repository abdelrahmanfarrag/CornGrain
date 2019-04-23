package com.example.corngrain.data.network.di

import okhttp3.logging.HttpLoggingInterceptor

interface LoggingInterceptor {
    fun loggingInterceptor():HttpLoggingInterceptor
}