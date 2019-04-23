package com.example.corngrain.data.network.response.movies

import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.google.gson.annotations.SerializedName

data class Popular(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<PopularEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)