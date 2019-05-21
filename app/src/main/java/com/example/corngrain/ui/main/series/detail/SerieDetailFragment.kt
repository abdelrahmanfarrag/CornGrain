package com.example.corngrain.ui.main.series.detail

import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.Credits
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.movies.details.adapter.CastAdapter
import com.example.corngrain.ui.main.movies.details.adapter.TrailersAdapter
import com.example.corngrain.ui.main.series.SeriesViewModel
import com.example.corngrain.ui.main.series.SeriesViewmodelFactory
import com.example.corngrain.ui.main.series.adapter.SerieCastAdapter
import com.example.corngrain.ui.main.series.adapter.SerieSeasonsAdapter
import com.example.corngrain.ui.main.youtube.YoutubeActivity
import com.example.corngrain.utilities.GlideApp
import kotlinx.android.synthetic.main.serie_detail_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SerieDetailFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val factory by instance<SeriesViewmodelFactory>()

    private lateinit var viewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.serie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val receiveId = arguments?.let { bundle ->
            SerieDetailFragmentArgs.fromBundle(bundle)
        }
        val id = receiveId?.serieId

        viewModel = ViewModelProviders.of(this, factory).get(SeriesViewModel::class.java)
        if (id != null)
            launch {
                viewModel.fetchDetails(id)
                    .observe(this@SerieDetailFragment, Observer { detail ->
                        if (detail == null) return@Observer
                        group_serie_detail_loading.visibility = View.INVISIBLE
                        detail_series_view.visibility = View.VISIBLE
                        buildDetailUI(detail)
                    })
                viewModel.fetchCredits(id).observe(this@SerieDetailFragment, Observer { credit ->
                    buildCastUi(credit)
                })
                viewModel.fetchReviews(id).observe(this@SerieDetailFragment, Observer { reviews ->
                    buildReviewsUI(reviews)
                })

            }
    }

    private fun buildReviewsUI(videos: Videos) {
        settingNormalRecyclerViewConfigs(
            this.context!!,
            videos.results.toTrailerAdapter(),
            serie_detail_videos_list,
            RecyclerView.VERTICAL,
            true
        ).setOnItemClickListener { item, view ->
            (item as TrailersAdapter).let { singleItem ->
                val intent = Intent(context!!, YoutubeActivity::class.java)
                intent.putExtra("key", singleItem.entry.key)
                startActivity(intent)
            }

        }
    }

    private fun buildDetailUI(detail: SerieDetail) {
        GlideApp.with(this)
            .load(BASE_IMG_URL + detail.backdropPath)
            .placeholder(R.drawable.ic_placeholder)
            .override(100)
            .into(serie_detail_backdrop)

        GlideApp.with(this)
            .load(BASE_IMG_URL + detail.posterPath)
            .into(serie_detail_poster)

        serie_detail_title.text = detail.originalName
        serie_detail_rating.rating = detail.voteAverage.toFloat() / 2f
        serie_detail_review_count.text = "${detail.voteCount} Reviewers"
        serie_detail_storyline_desc.text = detail.overview

        settingNormalRecyclerViewConfigs(
            this.context!!,
            detail.seasons.toSeasonsAdapter(),
            serie_detail_seasons_list,
            RecyclerView.HORIZONTAL
        )


    }

    private fun buildCastUi(credits: Credits) {
        settingNormalRecyclerViewConfigs(
            this.context!!,
            credits.cast.toAdapterItems(),
            serie_detail_cast_list,
            RecyclerView.HORIZONTAL
        )
    }

    private fun List<Credits.Cast>.toAdapterItems(): List<SerieCastAdapter> {
        return this.map { item ->
            SerieCastAdapter(item)
        }

    }

    private fun List<SerieDetail.Season>.toSeasonsAdapter(): List<SerieSeasonsAdapter> {
        return this.map { item ->
            SerieSeasonsAdapter(item)
        }
    }

    private fun List<Videos.Result>.toTrailerAdapter(): List<TrailersAdapter> {
        return this.map { item ->
            TrailersAdapter(item)
        }
    }
}
