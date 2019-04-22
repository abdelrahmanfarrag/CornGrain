package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.ui.base.ScopedFragment
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
        sample.text = job.get(0).originalTitle
    }

}
