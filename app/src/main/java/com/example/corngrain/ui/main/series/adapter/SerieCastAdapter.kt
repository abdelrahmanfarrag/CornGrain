package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_cast.*

class SerieCastAdapter(val entries: Credits.Cast) : BaseGroupeAdapter<Credits.Cast>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_cast

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(entries.profilePath, cast_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<Credits.Cast>): List<BaseGroupeAdapter<Credits.Cast>> {
        return entries.map { item ->
            SerieCastAdapter(item)
        }
    }

}