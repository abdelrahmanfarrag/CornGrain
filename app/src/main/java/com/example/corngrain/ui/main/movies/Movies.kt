package com.example.corngrain.ui.main.movies

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.data.network.response.movies.PopularMovies
import com.example.corngrain.data.network.response.movies.TopRatedMovies
import com.example.corngrain.data.network.response.movies.UpcomingMovies
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.*
import com.example.corngrain.utilities.GlideApp
import kotlinx.android.synthetic.main.now_playing_movies.*
import kotlinx.android.synthetic.main.popular_layout.*
import kotlinx.android.synthetic.main.rated_movies.*
import kotlinx.android.synthetic.main.upcoming_movies.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.math.sin

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
        val playMovies = viewModel.fetchPlayingMovies.await()
        val upcomingMovies = viewModel.fetchUpcomingMovies.await()
        val popularMovies = viewModel.fetchPopularMovies.await()
        val topRatedMovies = viewModel.fetchTopRatedMovies.await()
        buildingUpcomingMovieUI(upcomingMovies)
        buildingPlayingMovies(playMovies)
        buildingPopularMoviesUI(popularMovies)
        buildingTopRatedMoviesUI(topRatedMovies)
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

    private fun buildingPopularMoviesUI(popularMovies: LiveData<PopularMovies>) {
        popularMovies.observeForever { data ->
            if (popular_list != null)
                settingNormalRecyclerViewConfigs(
                    this.context!!,
                    data.results.toPopularAdapterItems(),
                    popular_list,
                    RecyclerView.VERTICAL
                )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buildingTopRatedMoviesUI(topRatedData: LiveData<TopRatedMovies>) {
        topRatedData.observeForever { data ->
            if (rated_movies_first_item != null)
                GlideApp.with(this.context!!)
                    .load(BASE_IMG_URL + data.results[0].backdropPath)
                    .into(rated_movies_first_item)

            if (rated_movies_first_item_title != null)
                rated_movies_first_item_title.text = data.results[0].title

            if (rated_first_movie_bar != null)
                rated_first_movie_bar.rating = data.results[0].voteAverage.toFloat() / 2f
            if (rated_movies_first_item_overview != null)
                rated_movies_first_item_overview.text = data.results[0].overview
            if (movies_rated_list != null)
                settingNormalRecyclerViewConfigs(
                    this.context!!,
                    data.results.toTopRatedMoviesAdapterItems(),
                    movies_rated_list,
                    RecyclerView.HORIZONTAL
                ).setOnItemClickListener { item, view ->
                    (item as TopRatedAdapter).let { singleItem->
                        toDetailScreen(singleItem.entry.id,view)
                    }
                }
        }
    }

    private fun toDetailScreen(id:Int,viewClicked :View){
        val actionWithValue = MoviesDirections.actionMoviesTabToMovieDetail(id)
        Navigation.findNavController(viewClicked).navigate(actionWithValue)

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

    private fun List<PopularMovies.Result>.toPopularAdapterItems(): List<PopularAdapter> {
        return this.map { item ->
            PopularAdapter(item)

        }
    }

    private fun List<TopRatedMovies.Result>.toTopRatedMoviesAdapterItems(): List<TopRatedAdapter> {
        return this.map { item ->
            TopRatedAdapter(item)
        }
    }


}
