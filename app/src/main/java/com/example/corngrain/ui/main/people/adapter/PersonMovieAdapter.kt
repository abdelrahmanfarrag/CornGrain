package com.example.corngrain.ui.main.people.adapter

import com.example.corngrain.R
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person_movies.*

class PersonMovieAdapter(private val entries: PersonMovies.Cast) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            setImage()
            movie_name.text = entries.originalTitle
        }
    }

    override fun getLayout(): Int = R.layout.item_person_movies

    private fun ViewHolder.setImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entries.posterPath)
            .placeholder(R.drawable.ic_placeholder)
            .into(person_movie_img)
    }

}