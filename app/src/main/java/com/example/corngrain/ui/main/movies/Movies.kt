package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.corngrain.R
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.data.network.response.movies.UpcomingMovies
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.*
import com.example.corngrain.utilities.GlideApp
import kotlinx.android.synthetic.main.now_playing_movies.*
import kotlinx.android.synthetic.main.upcoming_movies.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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
        val upcomingMovies = viewModel.fetchUpcomingMovies.await()
        buildingUpcomingMovieUI(upcomingMovies)
        buildingPlayingMovies(playMovies)

    }

    private fun buildingPlayingMovies(playingMovies: LiveData<PlayingMovies>) {
        playingMovies.observeForever { playing ->
            settingNormalRecyclerViewConfigs(
                this@Movies.context,
                playing.results.toAdapterItems(),
                now_playing_movies_list,
                LinearLayoutManager.HORIZONTAL
            )

        }


    }

    private fun buildingUpcomingMovieUI(upcomingMovies: LiveData<UpcomingMovies>) {
        upcomingMovies.observeForever { upcomingData ->
            if (upcoming_movies_first_img != null)
                GlideApp.with(this@Movies.context!!)
                    .load(BASE_IMG_URL + upcomingData.results[0].posterPath)
                    .into(upcoming_movies_first_img)

            if (upcoming_movies_second_img != null)
                GlideApp.with(this@Movies.context!!)
                    .load(BASE_IMG_URL + upcomingData.results[1].posterPath)
                    .into(upcoming_movies_second_img)
            val list: MutableList<UpcomingMovies.Result> = upcomingData.results.toMutableList()
            list.subList(0, 2).clear()

            if (upcoming_list != null)
                settingNormalRecyclerViewConfigs(
                    this@Movies.context!!
                    , list.toUpcomingMoviesData(),
                    upcoming_list,
                    LinearLayoutManager.HORIZONTAL
                )
        }


    }

    private fun List<PlayingMovies.Result>.toAdapterItems(): List<PlayingAdapter> {
        return this.map { item ->
            PlayingAdapter(item)
        }
    }

    private fun List<UpcomingMovies.Result>.toUpcomingMoviesData(): List<UpcomingAdapter> {
        return this.map { item ->
            UpcomingAdapter(item)
        }
    }


}
