package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.TopRatedSeries
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.rated_season_item.*

class RatedSeriesAdapter( val entry: TopRatedSeries.Result) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            rating.rating = entry.voteAverage.toFloat()/2.0f
         //   rating. = 1f
            setRatedImage()
        }
    }

    override fun getLayout(): Int = R.layout.rated_season_item


    private fun ViewHolder.setRatedImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath).placeholder(R.drawable.ic_placeholder)
            .into(rated_season_img)
    }

}