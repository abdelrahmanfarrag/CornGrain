package com.example.corngrain.ui.main.movies.details.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Similar
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_playing_movies.*

class SimilarAdapter(val entry:Similar.Result): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            playing_movie_title.text = entry.originalTitle
            playing_movie_rating.rating = entry.voteAverage.toFloat() / 2f
            reviews_count.text = "${entry.voteCount} Reviews"
            setSimilarMoviesImg()
        }

    }
    override fun getLayout(): Int = R.layout.item_playing_movies

    private fun ViewHolder.setSimilarMoviesImg() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .into(playing_movies_img)
    }
}