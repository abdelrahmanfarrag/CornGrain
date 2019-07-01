package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.TopRatedSeries
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.rated_season_item.*

class RatedSeriesAdapter(val entry: TopRatedSeries.Result) :
    BaseGroupeAdapter<TopRatedSeries.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.rated_season_item

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            rating.rating = entry.voteAverage.toFloat() / 2f
            setAdapterDisplayImage(entry.posterPath, rated_season_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<TopRatedSeries.Result>): List<BaseGroupeAdapter<TopRatedSeries.Result>> {
        return entries.map { item ->
            RatedSeriesAdapter(item)
        }
    }


}