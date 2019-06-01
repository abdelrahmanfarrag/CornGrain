package com.example.corngrain.data.network.di

import android.content.Context

interface LanguageQuery {
     fun setAppLocale(locale:String)
    fun getAppLocale(context: Context):String

}