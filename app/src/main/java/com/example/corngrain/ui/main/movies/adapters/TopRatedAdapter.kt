package com.example.corngrain.ui.main.movies.adapters

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*

class TopRatedAdapter(private val entry: TopRatedEntity) : Item() {


    override fun getLayout(): Int = R.layout.item_movie

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            movie_rating.text = entry.voteAverage.toString()
            settingTopRatedImage()
        }
    }

    private fun ViewHolder.settingTopRatedImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .into(movie_img)
    }
}