package com.example.corngrain.ui.main.people.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.known_for_item.*

class KnownForAdapter(private val entries: PopularPersons.Result.KnownFor) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            setKnownForImage()
        }
    }

    override fun getLayout(): Int = R.layout.known_for_item

    private fun ViewHolder.setKnownForImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entries.posterPath)
            .into(known_for_img)
    }
}