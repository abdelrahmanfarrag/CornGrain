package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.SerieCurrentlyShowing
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.inshow_item.*

class InshowAdapter(val data: SerieCurrentlyShowing.Result) :
    BaseGroupeAdapter<SerieCurrentlyShowing.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.inshow_item

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(data.posterPath, inshow_adpt_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<SerieCurrentlyShowing.Result>):
            List<BaseGroupeAdapter<SerieCurrentlyShowing.Result>> {
        return entries.map { item ->
            InshowAdapter(item)
        }
    }
}