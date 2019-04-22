package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
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
        val job = viewModel.fetchLatestMovies.await()
        initRecycler(job.toMoviesItems())
    }

    private fun List<PopularEntity>.toMoviesItems(): List<MoviesAdapter> {

        return this.map { itemEntity ->
            MoviesAdapter(itemEntity)
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


}
