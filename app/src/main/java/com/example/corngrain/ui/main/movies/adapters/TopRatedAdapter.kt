package com.example.corngrain.ui.main.movies.adapters

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.network.response.movies.TopRatedMovies
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*

class TopRatedAdapter(val entry: TopRatedMovies.Result) :
    BaseGroupeAdapter<TopRatedMovies.Result>() {
    override fun toGroupeAdapterItems(entries: List<TopRatedMovies.Result>): List<BaseGroupeAdapter<TopRatedMovies.Result>> {
        return entries.map {
            TopRatedAdapter(it)
        }
    }

    override fun setGroupeAdapterLayout(): Int = R.layout.item_movie

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            rated_movie_title.text = entry.originalTitle
            setAdapterDisplayImage(entry.posterPath, movie_img)
        }
    }


}