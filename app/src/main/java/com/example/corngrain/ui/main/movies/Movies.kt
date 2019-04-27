package com.example.corngrain.ui.main.movies

import android.media.Rating
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.response.movies.Playing
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.*
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.now_playing_movies.*
import kotlinx.android.synthetic.main.picked_movie_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class Movies : ScopedFragment(), KodeinAware {


    override val kodein: Kodein by closestKodein()

    private val factory by instance<MovieViewModelFactory>()


    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)
        buildUI()
    }

    @Suppress("ReplaceGetOrSet")
    private fun buildUI() = launch {
        val playMovies = viewModel.fetchLatestMovies.await()

        playMovies.observeForever { playing ->
            settingPlayingRecycler(playing.results.toAdapterItems())
            val generatedMovie = generateRandomNumber()
            val posterPath = playing.results.get(generatedMovie).posterPath
            val backdrop = playing.results.get(generatedMovie).backdropPath
            val rating = playing.results.get(generatedMovie).voteAverage.toFloat()
            val overView = playing.results.get(generatedMovie).overview
            settingPickedMovieUI(posterPath, backdrop, overView, rating)

        }

    }

    private fun List<PlayingMovies.Result>.toAdapterItems(): List<PlayingAdapter> {
        return this.map { item ->
            PlayingAdapter(item)
        }
    }

    private fun settingPickedMovieUI(
        posterPath: String,
        backdrop: String,
        overview: String,
        rating: Float
    ) {
        if (picked_movie_poster != null)
            GlideApp.with(this.context!!)
                .load(BASE_IMG_URL + posterPath)
                .into(picked_movie_poster)
        if (movie_backdrop != null)
            GlideApp.with(this.context!!)
                .load(BASE_IMG_URL + backdrop)
                .into(movie_backdrop)
        if (movie_rating != null)
            movie_rating.rating = rating / 2f
        if (picked_movie_overview != null)
            picked_movie_overview.text = overview
    }

    private fun settingPlayingRecycler(entries: List<PlayingAdapter>) {
        val group = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        if (now_playing_movies_list != null)
            now_playing_movies_list.apply {
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = group
            }
    }

    private fun generateRandomNumber(): Int {
        return (0..19).random()
    }

}
