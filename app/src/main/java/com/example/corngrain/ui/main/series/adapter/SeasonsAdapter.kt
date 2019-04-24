package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.single_season_item.*

class SeasonsAdapter(private val entry: SerieDetail.Season) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            seasonImage()
        }
    }

    override fun getLayout(): Int = R.layout.single_season_item


    fun ViewHolder.seasonImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .into(detail_season_img)
    }
}