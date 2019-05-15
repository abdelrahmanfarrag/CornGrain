package com.example.corngrain.ui.main.search.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_search.*

class SearchAdapter( val entries: MovieSearch.Result) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            loadSearchImage()
            search_movie_title.text = entries.title
            search_movie_rating_value.text = entries.voteAverage.toString()
            search_movie_overview.text = entries.overview
        }
    }

    override fun getLayout(): Int = R.layout.item_search


    fun ViewHolder.loadSearchImage() {
            GlideApp.with(this.containerView)
                .load(BASE_IMG_URL + entries.backdropPath)
                .placeholder(R.drawable.ic_placeholder)
                .into(search_movie_img)
    }

}