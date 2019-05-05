package com.example.corngrain.ui.main.movies.adapters

import android.annotation.SuppressLint
import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.data.network.response.movies.PopularMovies
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_popular.*


const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

class PopularAdapter(private val entry: PopularMovies.Result) : Item() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            if (entry.originalTitle.length <30) {
                popular_movie_name_and_rating.text =
                    "${entry.originalTitle} ( ${entry.voteAverage} /10)"
            }else{
                val text = entry.originalTitle.substring(0,29)
                popular_movie_name_and_rating.text =
                    "$text( ${entry.voteAverage} /10)"

            }
            if (entry.overview.length >400){
                val text = entry.overview.substring(0,399)
                popular_overview.text = "$text+ ..."


            }else{
                popular_overview.text = entry.overview

            }
            production_date.text = entry.releaseDate
            settingWeatherImage()
        }
    }

    override fun getLayout(): Int = R.layout.item_popular

    private fun ViewHolder.settingWeatherImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entry.posterPath)
            .into(popular_movie_img)

    }
}