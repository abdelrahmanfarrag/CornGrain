package com.example.corngrain.ui.main.trending.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.trending_serie_item.*

class TrendingSeriesAdapter(val entries: SeriesAndTvShows.Result) :
    BaseGroupeAdapter<SeriesAndTvShows.Result>() {

    override fun setGroupeAdapterLayout(): Int = R.layout.trending_serie_item

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(entries.posterPath, trending_serie_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<SeriesAndTvShows.Result>): List<BaseGroupeAdapter<SeriesAndTvShows.Result>> {
        return entries.map { item ->
            TrendingSeriesAdapter(item)
        }
    }
}
