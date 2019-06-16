package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.seasons_item.*

class SerieSeasonsAdapter(val entries: SerieDetail.Season) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            serie_title.text=entries.name
            bindImage()
        }
    }

    override fun getLayout(): Int = R.layout.seasons_item

    fun ViewHolder.bindImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entries.posterPath)
            .into(season_serie_img)
    }
}