package com.example.corngrain.ui.main.movies.adapters

import android.annotation.SuppressLint
import com.example.corngrain.R
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_playing_movies.*

class PlayingAdapter(val entry: PlayingMovies.Result) : BaseGroupeAdapter<PlayingMovies.Result>() {



    override fun setGroupeAdapterLayout(): Int = R.layout.item_playing_movies

    @SuppressLint("SetTextI18n")
    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            playing_movie_title.text = entry.originalTitle
            playing_movie_rating.rating = entry.voteAverage.toFloat() / 2f
            reviews_count.text = "${entry.voteCount} Reviews"
            setAdapterDisplayImage(entry.posterPath, playing_movies_img)
        }
    }
    override fun toGroupeAdapterItems(entries: List<PlayingMovies.Result>): List<BaseGroupeAdapter<PlayingMovies.Result>> {
        return entries.map { item ->
            PlayingAdapter(item)
        }
    }

}