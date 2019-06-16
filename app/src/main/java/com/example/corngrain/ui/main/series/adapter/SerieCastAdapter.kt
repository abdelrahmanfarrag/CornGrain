package com.example.corngrain.ui.main.series.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_cast.*

class SerieCastAdapter(val entries:Credits.Cast):Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            setImage()
            cast_name.text = entries.name
            if (entries.character.length > 40) {
                val minimizedCharName = entries.character.substring(0, 39)
                cast_char.text = "$minimizedCharName..."
            } else {
                cast_char.text = entries.character
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_cast

    private fun ViewHolder.setImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entries.profilePath)
            .placeholder(R.drawable.ic_placeholder)
            .into(cast_img)
    }

}