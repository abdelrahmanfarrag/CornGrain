package com.example.corngrain.ui.main.movies.adapters

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.response.movies.UpcomingMovies
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*


class UpcomingAdapter( val upcomingMovies: UpcomingMovies.Result) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            setUpcomingMoviePoster()
        }
    }

    override fun getLayout(): Int = R.layout.item_movie

    private fun ViewHolder.setUpcomingMoviePoster() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + upcomingMovies.posterPath)
            .into(movie_img)
    }

}