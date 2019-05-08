package com.example.corngrain.ui.main.movies.adapters

import android.annotation.SuppressLint
import android.util.Log
import com.example.corngrain.R
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_playing_movies.*

class PlayingAdapter(val entry: PlayingMovies.Result) : Item() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.d("executed", entry.posterPath)

        viewHolder.apply {
            playing_movie_title.text = entry.originalTitle
            playing_movie_rating.rating = entry.voteAverage.toFloat() / 2f
            reviews_count.text = "${entry.voteCount} Reviews"
            setPlayingImage()
        }
    }

    override fun getLayout(): Int = R.layout.item_playing_movies

    private fun ViewHolder.setPlayingImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .into(playing_movies_img)
    }
}