package com.example.corngrain.data.network.di

import android.content.Context

class LanguageQueryImpl(private val context: Context) : LanguageQuery {
    override fun getAppLocale(context: Context): String {
        val pref =
            context.getSharedPreferences("local_notification_data", Context.MODE_PRIVATE)
        val locale = pref?.getString("locale", "No data")
        return locale!!

    }

    override fun setAppLocale(locale: String) {
        val preferences = context.getSharedPreferences(
            "local_notification_data",
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putString("locale", locale)
        editor?.apply()
        //resources.updateConfiguration(configuration, displayMetrices)

    }
}