package com.example.corngrain.data.network.di

import android.content.Context
import java.util.*

@Suppress("DEPRECATION")
class LanguageQueryImpl(private val context: Context) : LanguageQuery {
    override fun getAppLocale(): String {
        val pref =
            context.getSharedPreferences("local_notification_data", Context.MODE_PRIVATE)
        val locale = pref?.getString("locale", "No data")
        return locale!!

    }

    override fun setAppLocale(locale: String) {
        val resources = context.resources
        val displayMetrices = resources.displayMetrics
        val configuration = resources.configuration
        configuration.setLocale(Locale(locale.toLowerCase()))
        val preferences = context.getSharedPreferences(
            "local_notification_data",
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putString("locale", locale)
        editor?.apply()
        resources.updateConfiguration(configuration, displayMetrices)

    }
}