package com.example.corngrain.ui.main.movies.adapters

import android.annotation.SuppressLint
import com.example.corngrain.R
import com.example.corngrain.data.network.response.movies.PopularMovies
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_popular.*

class PopularMoviesAdapter(val entry: PopularMovies.Result) :
    BaseGroupeAdapter<PopularMovies.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_popular

    @SuppressLint("SetTextI18n")
    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
               if (entry.originalTitle.length < 30) {
                   popular_movie_name_and_rating.text =
                       "${entry.originalTitle} ( ${entry.voteAverage} /10)"
               } else {
                   val text = entry.originalTitle.substring(0, 29)
                   popular_movie_name_and_rating.text =
                       "$text( ${entry.voteAverage} /10)"
               }
               if (entry.overview.length > 400) {
                   val text = entry.overview.substring(0, 399)
                   popular_overview.text = "$text+ ..."
               } else {
                   popular_overview.text = entry.overview
               }
               production_date.text = entry.releaseDate
            setAdapterDisplayImage(entry.posterPath, popular_movie_img)
        }

    }

    override fun toGroupeAdapterItems(entries: List<PopularMovies.Result>): List<BaseGroupeAdapter<PopularMovies.Result>> {
        return entries.map {
            PopularMoviesAdapter(it)
        }
    }
}