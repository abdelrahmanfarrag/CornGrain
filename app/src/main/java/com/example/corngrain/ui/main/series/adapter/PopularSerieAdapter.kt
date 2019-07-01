package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_popular_serie.*

class PopularSerieAdapter(val entry: PopularSeriesEntity) :
    BaseGroupeAdapter<PopularSeriesEntity>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_popular_serie

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(entry.posterPath, item_serie_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<PopularSeriesEntity>): List<BaseGroupeAdapter<PopularSeriesEntity>> {
        return entries.map { item ->
            PopularSerieAdapter(item)
        }
    }
}