package com.example.corngrain.ui.main.movies.details

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Detail
import com.example.corngrain.data.network.response.movies.MovieCredits
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.movies.details.adapter.CastAdapter
import com.example.corngrain.ui.main.movies.details.adapter.ReviewsAdapter
import com.example.corngrain.utilities.GlideApp
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import java.lang.StringBuilder
import java.text.DecimalFormat

class MovieDetail : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val movieDetailViewModelInstanceFactory: ((Int) -> MovieDetailViewModelFactory) by factory()


    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val safedMovieId = arguments?.let { bundle ->
            MovieDetailArgs.fromBundle(bundle)
        }
        val id = safedMovieId?.id
        viewModel = ViewModelProviders.of(this, movieDetailViewModelInstanceFactory(id!!))
            .get(MovieDetailViewModel::class.java)
        bindDetailUI()
        bindCastUI()
        bindReviewUI()
        //  testing_pass.text = safedMovieId?.id.toString()
        // testing_pass.gravity=Gravity.CENTER

    }


    @SuppressLint("SetTextI18n")
    private fun bindDetailUI() {
        launch {
            val detailedData = viewModel.fetchMovieDetail.await()
            detailedData.observe(this@MovieDetail, Observer { data ->
                GlideApp.with(this@MovieDetail)
                    .load(BASE_IMG_URL + data.backdropPath)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(detail_screen_image)
                detail_screen_title.text = data.originalTitle
                detail_screen_tagline.text = data.tagline
                detail_screen_genres.text = setGenres(data.genres).toString()
                detail_screen_rating.rating = data.voteAverage.toFloat() / 2f
                val reviewersInDecimal = DecimalFormat("#,###")
                val profitDecimal = DecimalFormat("#,###,###")
                detail_screen_rating_reviewers_count.text =
                    "${reviewersInDecimal.format(data.voteCount)} Reviewers"
                detail_screen_year_value.text = data.releaseDate
                detail_screen_lang_value.text = data.originalLanguage
                detail_screen_runtime_value.text = "${data.runtime} MIN"
                val profit = data.revenue - data.budget

                detail_screen_profit_value.compoundDrawablePadding = 4
                detail_screen_profit_value.text = "${profitDecimal.format(profit)} M"

                if (profit > 0) {
                    detail_screen_profit_value.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_profit,
                        0,
                        0,
                        0
                    )
                } else {
                    detail_screen_profit_value.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_lost,
                        0,
                        0,
                        0
                    )
                }
                detail_screen_story_value.text = data.overview

            })
        }
    }

    private fun bindCastUI() {
        launch {
            val cast = viewModel.fetchMovieCast.await()
            cast.observe(this@MovieDetail, Observer { castData ->
                settingNormalRecyclerViewConfigs(
                    context!!,
                    castData.cast.toCastAdapter(),
                    detail_screen_cast_list,
                    RecyclerView.HORIZONTAL
                )

            })
        }
    }

    private fun bindReviewUI() {
        launch {
            val reviews = viewModel.fetchMovieReviews.await()
            reviews.observe(this@MovieDetail, Observer { reviews ->
                val reviewsAdapter = ReviewsAdapter(reviews.results)
                reviews_pager.adapter = reviewsAdapter
                autoPagerSlide(reviews_pager, reviews_layout, reviews.results.size, 5000)

            })
        }
    }

    private fun List<MovieCredits.Cast>.toCastAdapter(): List<CastAdapter> {
        return this.map { item ->
            CastAdapter(item)
        }
    }

    private fun setGenres(genresList: List<Detail.Genre>): StringBuilder {
        val stringBuilder = StringBuilder()
        genresList.forEachIndexed { index, genre ->

            when (index) {
                (genresList.size - 1) -> stringBuilder.append(genre.name)
                else -> stringBuilder.append("${genre.name} ,")
            }
        }
        return stringBuilder
    }

}
