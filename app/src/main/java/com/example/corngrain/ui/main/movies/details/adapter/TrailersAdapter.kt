package com.example.corngrain.ui.main.movies.details.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.trailer_item.*

const val BASE_YOUTUBE_IMG_URL = "https://img.youtube.com/vi/"

class TrailersAdapter( val entry: Videos.Result) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            trailerImage()
            trailer_type.text = entry.type
            trailer_name.text = entry.name
        }
    }

    override fun getLayout(): Int = R.layout.trailer_item


    private fun ViewHolder.trailerImage() {
        GlideApp.with(this.containerView)
            .load(BASE_YOUTUBE_IMG_URL + entry.key + "/0.jpg")
            .placeholder(R.drawable.ic_placeholder)
            .into(trailer_image)
    }
}