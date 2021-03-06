package com.example.corngrain.data.network.response.movies

import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.google.gson.annotations.SerializedName

data class TopRated(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TopRatedEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)