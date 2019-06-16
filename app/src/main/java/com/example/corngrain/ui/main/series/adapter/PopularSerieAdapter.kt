package com.example.corngrain.ui.main.series.adapter

import com.bumptech.glide.Glide
import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_popular_serie.*

class PopularSerieAdapter( val entry: PopularSeriesEntity) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            setPopularSerieImage()
        }
    }

    override fun getLayout(): Int = R.layout.item_popular_serie

    private fun ViewHolder.setPopularSerieImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .placeholder(R.drawable.ic_placeholder)
            .into(item_serie_img)
    }
}