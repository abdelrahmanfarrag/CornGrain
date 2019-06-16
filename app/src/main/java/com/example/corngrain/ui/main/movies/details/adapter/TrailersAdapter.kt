package com.example.corngrain.ui.main.movies.details.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.trailer_item.*

const val BASE_YOUTUBE_IMG_URL = "https://img.youtube.com/vi/"

class TrailersAdapter(val entry: Videos.Result) : BaseGroupeAdapter<Videos.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.trailer_item

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {

        viewHolder.apply {
            GlideApp.with(this.containerView)
                .load(BASE_YOUTUBE_IMG_URL + entry.key + "/0.jpg")
                .placeholder(R.drawable.ic_placeholder)
                .into(trailer_image)

            //  setAdapterDisplayImage(entry.key + "/0.jpg", trailer_image)
            trailer_type.text = entry.type
            trailer_name.text = entry.name
        }
    }

    override fun toGroupeAdapterItems(entries: List<Videos.Result>): List<BaseGroupeAdapter<Videos.Result>> {
        return entries.map { item ->
            TrailersAdapter(item)
        }
    }
}