package com.example.corngrain.ui.main.trending.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.trending_serie_item.*

class TrendingSeriesAdapter( val entries:SeriesAndTvShows.Result):Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            bindImage()
        }
    }

    override fun getLayout(): Int = R.layout.trending_serie_item

    fun ViewHolder.bindImage(){
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL+entries.posterPath)
            .into(trending_serie_img)
    }
}