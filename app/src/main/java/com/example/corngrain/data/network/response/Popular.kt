package com.example.corngrain.data.network.response

import com.example.corngrain.data.db.entity.PopularEntity
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