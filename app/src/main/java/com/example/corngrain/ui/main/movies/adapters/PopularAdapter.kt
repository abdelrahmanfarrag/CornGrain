package com.example.corngrain.ui.main.movies.adapters

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*


const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

class MoviesAdapter(private val entry: PopularEntity) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            movie_rating.text = entry.voteAverage.toString()
            settingWeatherImage()
        }
    }

    override fun getLayout(): Int = R.layout.item_movie

    private fun ViewHolder.settingWeatherImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .into(movie_img)

    }
}