package com.example.corngrain.ui.main.movies.details

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Detail
import com.example.corngrain.ui.base.BaseFragment
import com.example.corngrain.ui.main.movies.details.adapter.CastAdapter
import com.example.corngrain.ui.main.movies.details.adapter.ReviewsAdapter
import com.example.corngrain.ui.main.movies.details.adapter.SimilarAdapter
import com.example.corngrain.ui.main.movies.details.adapter.TrailersAdapter
import com.example.corngrain.ui.main.youtube.YoutubeActivity
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.example.corngrain.utilities.normalRecyclerView
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import java.lang.StringBuilder
import java.text.DecimalFormat

class MovieDetail : BaseFragment(), KodeinAware {


    override val kodein: Kodein by closestKodein()
    private val movieDetailViewModelInstanceFactory: ((Int) -> MovieDetailViewModelFactory) by factory()
    private lateinit var viewModel: MovieDetailViewModel


    override fun setFragmentLayout(): Int = R.layout.movie_detail_fragment

    override fun bindFragmentUI() {
        val safedMovieId = arguments?.let { bundle ->
            MovieDetailArgs.fromBundle(bundle)

        }
        val id = safedMovieId?.id
        viewModel = ViewModelProviders.of(this, movieDetailViewModelInstanceFactory(id!!))
            .get(MovieDetailViewModel::class.java)
        bindDetailUI()
        bindCastUI()
        bindReviewUI()
        bindTrailersUI()
        bindSimilarMovieUI()

    }


    @SuppressLint("SetTextI18n")
    private fun bindDetailUI() {

        launch {
            val detailedData = viewModel.fetchMovieDetail.await()
            detailedData.observe(this@MovieDetail, Observer { data ->
                if (data == null) return@Observer
                else {
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
                    val reviewers = resources.getString(R.string.reviewers)
                    detail_screen_rating_reviewers_count.text =
                        "${reviewersInDecimal.format(data.voteCount)} $reviewers"
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
                }
            })

        }
    }

    private fun bindCastUI() {
        launch {
            val cast = viewModel.fetchMovieCast.await()
            cast.observe(this@MovieDetail, Observer { castData ->
                val castAdapter = CastAdapter(castData.cast[0])
                normalRecyclerView(
                    context!!,
                    castAdapter.toGroupeAdapterItems(castData.cast),
                    RecyclerView.HORIZONTAL
                    , detail_screen_cast_list
                )

            })
        }
    }

    private fun bindReviewUI() {
        launch {
            val reviews = viewModel.fetchMovieReviews.await()
            reviews.observe(this@MovieDetail, Observer { reviewsData ->
                val reviewsAdapter = ReviewsAdapter(reviewsData.results[0])
                if (reviewsData == null) return@Observer
                if (reviewsData.results.isNotEmpty()) {
                    normalRecyclerView(
                        this@MovieDetail.context,
                        reviewsAdapter.toGroupeAdapterItems(reviewsData.results),
                        RecyclerView.VERTICAL,
                        reviews_list
                    )
                } else {
                    no_review_tv.visibility = View.VISIBLE
                }


            })

        }
    }


    private fun bindTrailersUI() {
        launch {
            val trailers = viewModel.fetchMovieTrailers.await()
            trailers.observe(this@MovieDetail, Observer { videos ->
                val videosAdapter = TrailersAdapter(videos.results[0])
                if (videos == null) return@Observer
                if (videos.results.isNotEmpty()) {
                    normalRecyclerView(
                        context!!,
                        videosAdapter.toGroupeAdapterItems(videos.results),
                        RecyclerView.HORIZONTAL,
                        videos_list
                    ).setOnItemClickListener { item, _ ->
                        (item as TrailersAdapter).let { singleItem ->
                            val intent = Intent(context!!, YoutubeActivity::class.java)
                            intent.putExtra("key", singleItem.entry.key)
                            startActivity(intent)
                        }

                    }
                } else {
                    no_videos_tv.visibility = View.VISIBLE
                }

            })
        }
    }

    private fun bindSimilarMovieUI() {
        launch {
            val similar = viewModel.fetchSimilarMovies.await()
            similar.observe(this@MovieDetail, Observer { similarDetails ->
                val similarAdapter = SimilarAdapter(similarDetails.results[0])
                if (similarDetails == null) return@Observer
                else {
                    loading_container.visibility = View.INVISIBLE
                    views_container.visibility = View.VISIBLE
                    if (similarDetails.results.isNotEmpty()) {
                        normalRecyclerView(
                            context!!,
                            similarAdapter.toGroupeAdapterItems(similarDetails.results),
                            RecyclerView.HORIZONTAL,
                            similar_list
                        )
                    } else {
                        no_similar_tv.visibility = View.VISIBLE
                    }
                }
            })
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
