package com.example.corngrain.ui.main.movies.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.corngrain.R
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetail : Fragment() {


    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        val safedMovieId = arguments?.let { bundle ->
            MovieDetailArgs.fromBundle(bundle)
        }
        testing_pass.text = safedMovieId?.id.toString()

    }

}
