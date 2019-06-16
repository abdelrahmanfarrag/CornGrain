package com.example.corngrain.ui.main.search.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_search.*

class SearchAdapter(val entries: MovieSearch.Result) : BaseGroupeAdapter<MovieSearch.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_search

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            setAdapterDisplayImage(entries.backdropPath, search_movie_img)
            search_movie_title.text = entries.title
            search_movie_rating_value.text = entries.voteAverage.toString()
            search_movie_overview.text = entries.overview
        }

    }
    override fun toGroupeAdapterItems(entries: List<MovieSearch.Result>): List<BaseGroupeAdapter<MovieSearch.Result>> {
        return entries.map { item ->
            SearchAdapter(item)
        }
    }
}