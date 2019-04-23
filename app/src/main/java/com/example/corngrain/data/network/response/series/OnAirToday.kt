package com.example.corngrain.data.network.response.series

import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.google.gson.annotations.SerializedName

data class OnAirToday(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<OnAirTodayEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
}