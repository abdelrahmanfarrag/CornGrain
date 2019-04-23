package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.data.db.entity.TopRatedEntity
import com.example.corngrain.data.db.entity.UpcomingEntity
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.MoviesAdapter
import com.example.corngrain.ui.main.movies.adapters.TopRatedAdapter
import com.example.corngrain.ui.main.movies.adapters.UpcomingAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.movies_fragment.*
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
    private fun buildUI() {
        upcoming_movies_btn.setOnClickListener {
            settingUpcomingClick()
        }
        movie_popular_btn.setOnClickListener {
            settingPopularClick()
        }
        toprated_movies_btn.setOnClickListener {
            settingTopRatedClick()
        }
    }

    private fun settingUpcomingClick() {
        movies_list.adapter = null
        launch {
            val job = viewModel.fetchUpcomingMovies.await()
            initUpcomingRecycler(job.toUpcomingItems())
        }
    }

    private fun settingPopularClick() {
        movies_list.adapter = null
        launch {
            val job = viewModel.fetchLatestMovies.await()
            initRecycler(job.toMoviesItems())

        }
    }

    private fun settingTopRatedClick() {
        movies_list.adapter = null
        launch {
            val topRatedJob = viewModel.fetchTopRatedMovies.await()
            Log.d("items",topRatedJob.size.toString())
            initTopRatedRecycler(topRatedJob.toTopRatedItems())
        }
    }

    private fun List<PopularEntity>.toMoviesItems(): List<MoviesAdapter> {

        return this.map { itemEntity ->
            MoviesAdapter(itemEntity)
        }
    }

    private fun List<UpcomingEntity>.toUpcomingItems(): List<UpcomingAdapter> {
        return this.map { itemUpcoming ->
            UpcomingAdapter(itemUpcoming)
        }
    }

    private fun List<TopRatedEntity>.toTopRatedItems(): List<TopRatedAdapter> {
        return this.map { topRatedItem ->
            TopRatedAdapter(topRatedItem)
        }
    }


    private fun initRecycler(entries: List<MoviesAdapter>) {
        val groupie_adapter = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        movies_list.apply {
            layoutManager =
                LinearLayoutManager(this@Movies.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupie_adapter

        }
    }


    private fun initUpcomingRecycler(entries: List<UpcomingAdapter>) {
        val groupie_adapter = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        movies_list.apply {
            layoutManager =
                LinearLayoutManager(this@Movies.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupie_adapter

        }
    }

    private fun initTopRatedRecycler(entries: List<TopRatedAdapter>) {
        val groupieAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        movies_list.apply {
            layoutManager =
                LinearLayoutManager(this@Movies.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupieAdapter

        }

    }


}
