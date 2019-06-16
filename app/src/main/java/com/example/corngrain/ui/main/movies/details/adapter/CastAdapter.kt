package com.example.corngrain.ui.main.movies.details.adapter

import android.annotation.SuppressLint
import com.example.corngrain.R
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_cast.*

class CastAdapter(private val entries: Credits.Cast) : BaseGroupeAdapter<Credits.Cast>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_cast

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            cast_name.text = entries.name
            if (entries.character.length > 40) {
                val minimizedCharName = entries.character.substring(0, 39)
                cast_char.text = "$minimizedCharName..."
            } else {
                cast_char.text = entries.character
            }
            setAdapterDisplayImage(entries.profilePath, cast_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<Credits.Cast>): List<BaseGroupeAdapter<Credits.Cast>> {
        return entries.map { item ->
            CastAdapter(item)
        }
    }

}