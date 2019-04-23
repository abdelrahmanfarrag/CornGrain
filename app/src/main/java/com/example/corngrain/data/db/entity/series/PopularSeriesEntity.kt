package com.example.corngrain.data.db.entity.series

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv_popular", indices = [Index(value = ["id"], unique = true)])
data class PopularSeriesEntity(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @PrimaryKey(autoGenerate = true)
    val popularSeriesId: Int? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int

)