package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.single_season_item.*


class SeasonsAdapter(private val entry: SerieDetail.Season) :
    BaseGroupeAdapter<SerieDetail.Season>() {

    override fun setGroupeAdapterLayout(): Int = R.layout.single_season_item


    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(entry.posterPath, detail_season_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<SerieDetail.Season>): List<BaseGroupeAdapter<SerieDetail.Season>> {
        return entries.map { item ->
            SeasonsAdapter(item)
        }
    }

}