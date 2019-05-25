package com.example.corngrain.data.network.response.series

import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("_id")
    val season_id: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("videos")
    val videos: Videos
) {
    data class Videos(
        @SerializedName("results")
        val results: List<Result>
    ) {
        data class Result(
            @SerializedName("id")
            val id: String,
            @SerializedName("iso_3166_1")
            val iso31661: String,
            @SerializedName("iso_639_1")
            val iso6391: String,
            @SerializedName("key")
            val key: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("site")
            val site: String,
            @SerializedName("size")
            val size: Int,
            @SerializedName("type")
            val type: String
        )
    }

    data class Episode(
        @SerializedName("air_date")
        val airDate: String,
        @SerializedName("crew")
        val crew: List<Crew>,
        @SerializedName("episode_number")
        val episodeNumber: Int,
        @SerializedName("guest_stars")
        val guestStars: List<GuestStar>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("production_code")
        val productionCode: String,
        @SerializedName("season_number")
        val seasonNumber: Int,
        @SerializedName("show_id")
        val showId: Int,
        @SerializedName("still_path")
        val stillPath: String,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
    ) {
        data class GuestStar(
            @SerializedName("character")
            val character: String,
            @SerializedName("credit_id")
            val creditId: String,
            @SerializedName("gender")
            val gender: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("order")
            val order: Int,
            @SerializedName("profile_path")
            val profilePath: String
        )

        data class Crew(
            @SerializedName("credit_id")
            val creditId: String,
            @SerializedName("department")
            val department: String,
            @SerializedName("gender")
            val gender: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("job")
            val job: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("profile_path")
            val profilePath: String
        )
    }
}