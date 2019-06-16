package com.example.corngrain.ui.main.movies.adapters

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.response.movies.UpcomingMovies
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*


class UpcomingAdapter(val upcomingMovies: UpcomingMovies.Result) :
    BaseGroupeAdapter<UpcomingMovies.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_movie

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(upcomingMovies.posterPath, movie_img)
        }
    }

    override fun toGroupeAdapterItems(entries: List<UpcomingMovies.Result>): List<BaseGroupeAdapter<UpcomingMovies.Result>> {
        return entries.map { item ->
            UpcomingAdapter(item)
        }
    }
}