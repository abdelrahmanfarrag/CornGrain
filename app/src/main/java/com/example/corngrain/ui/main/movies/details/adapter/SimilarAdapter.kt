package com.example.corngrain.ui.main.movies.details.adapter

import android.annotation.SuppressLint
import com.example.corngrain.R
import com.example.corngrain.data.network.response.Similar
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_playing_movies.*

class SimilarAdapter(val entry: Similar.Result) : BaseGroupeAdapter<Similar.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_playing_movies

    @SuppressLint("SetTextI18n")
    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            playing_movie_title.text = entry.originalTitle
            playing_movie_rating.rating = entry.voteAverage.toFloat() / 2f
            reviews_count.text = "${entry.voteCount} Reviews"
            setAdapterDisplayImage(entry.backdropPath, playing_movies_img)
        }

    }

    override fun toGroupeAdapterItems(entries: List<Similar.Result>): List<BaseGroupeAdapter<Similar.Result>> {
        return entries.map { item ->
            SimilarAdapter(item)
        }
    }
}