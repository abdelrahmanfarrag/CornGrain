package com.example.corngrain.data.network.response.movies

import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.google.gson.annotations.SerializedName

data class Upcoming(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<UpcomingEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    data class Dates(
        @SerializedName("maximum")
        val maximum: String,
        @SerializedName("minimum")
        val minimum: String
    )
}