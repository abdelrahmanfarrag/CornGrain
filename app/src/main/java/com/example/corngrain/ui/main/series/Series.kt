package com.example.corngrain.ui.main.series

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.data.network.response.series.TopRatedSeries
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.series.adapter.*
import com.example.corngrain.utilities.GlideApp
import kotlinx.android.synthetic.main.item_on_air.*
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.popular_series.*
import kotlinx.android.synthetic.main.rated_seasons.*
import kotlinx.android.synthetic.main.serie_detail_card.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.StringBuilder

class Series : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val factory by instance<SeriesViewmodelFactory>()
    private lateinit var viewModel: SeriesViewModel

    private var movieId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.series_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(SeriesViewModel::class.java)
        bindUI()
        bindUI2()
    }

    private fun bindUI() = launch {
        val job = viewModel.fetchSeries.await()
        job.observe(this@Series, Observer { onAirJob ->
            val pagerAdapter = OnAirTodayAdapter(onAirJob)
            today_series_pager.adapter = pagerAdapter
            movieId = onAirJob.results[generateRandomizedNumber()].id
            autoPagerSlide(today_series_pager, dots_layout, pagerAdapter.count)

        })

        val popularSeries = viewModel.fetchPopularSeries.await()
        GlideApp.with(context!!)
            .load(BASE_IMG_URL + popularSeries[0].posterPath)
            .into(first_item_img)
        first_item_img.setOnClickListener {
            toSerieDetailScreen(popularSeries[0].id, it)
        }
        popularSeries.removeAt(0)
        settingNormalRecyclerViewConfigs(
            this@Series.context,
            popularSeries.toAdapterItems(),
            popular_serie_list,
            RecyclerView.HORIZONTAL,
            true
        ).setOnItemClickListener { item, view ->
            (item as PopularSerieAdapter).let {
                toSerieDetailScreen(it.entry.id, view)
            }
        }


        val randomSerieDetail = viewModel.fetchDetails(movieId)
        detailCardUI(randomSerieDetail)

        val topRatedSeries = viewModel.fetchTopRatedSeries.await()

        topRatedSeries.observe(this@Series, Observer {
            settingNormalRecyclerViewConfigs(
                this@Series.context!!,
                it.results.toRatedSeriesItems(),
                rated_seasons_list,
                RecyclerView.HORIZONTAL
            ).setOnItemClickListener { item, view ->
                (item as RatedSeriesAdapter).let {
                    toSerieDetailScreen(it.entry.id, view)
                }
            }
        })

    }

    private fun bindUI2() = launch(Dispatchers.Main) {
        val inshowSeries = viewModel.fetchInViewSeries.await()
        inshowSeries.observe(this@Series, Observer { data ->
            val onAirPagerAdapter = OnAirAdapter(data.results)
            on_air_series_pager.adapter = onAirPagerAdapter
            autoPagerSlide(on_air_series_pager, dots_layout, data.results.size, 7000)


        })
    }


    private fun setGenres(genresList: List<SerieDetail.Genre>): StringBuilder {
        val stringBuilder = StringBuilder()
        genresList.forEachIndexed { index, genre ->

            when (index) {
                (genresList.size - 1) -> stringBuilder.append(genre.name)
                else -> stringBuilder.append("${genre.name} ,")
            }
        }
        return stringBuilder
    }

    @SuppressLint("SetTextI18n")
    private fun detailCardUI(data: LiveData<SerieDetail>) {
        data.observe(this, Observer {
            serie_detail_title?.text = it.originalName
            serie_detail_overview?.text = it.overview
            serie_detail_rating?.text = "Rating \n ${it.voteAverage}"
            serie_detail_episodes?.text = "Episodes \n ${it.numberOfEpisodes}"
            serie_detail_seasons?.text = "Seasons \n ${it.numberOfSeasons}"
            season_genres?.text = setGenres(it.genres).toString()
            settingNormalRecyclerViewConfigs(
                this.context, it.seasons.toSeasonDetail(), seasons_list, RecyclerView
                    .HORIZONTAL
            )
            GlideApp.with(this.context!!)
                .load(BASE_IMG_URL + data.value?.posterPath)
                .placeholder(R.drawable.ic_placeholder)
                .into(serie_detail_poster)
            GlideApp.with(this.context!!)
                .load(BASE_IMG_URL + data.value?.backdropPath)
                .placeholder(R.drawable.ic_placeholder)
                .into(serie_detail_backdrop)
            seasons_info.setOnClickListener {
                val uri = Uri.parse(data.value?.homepage)
                val intents = Intent(Intent.ACTION_VIEW, uri)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                context?.startActivity(intents)

            }
        })


    }


    private fun List<PopularSeriesEntity>.toAdapterItems(): List<PopularSerieAdapter> {
        return this.map { item ->
            PopularSerieAdapter(item)
        }
    }

    private fun List<SerieDetail.Season>.toSeasonDetail(): List<SeasonsAdapter> {
        return this.map { item ->
            SeasonsAdapter(item)
        }

    }

    private fun List<TopRatedSeries.Result>.toRatedSeriesItems(): List<RatedSeriesAdapter> {
        return this.map { item ->
            RatedSeriesAdapter(item)

        }
    }

    private fun toSerieDetailScreen(id: Int, view: View) {
        val action = SeriesDirections.serieDetailAction(id)
        Navigation.findNavController(view).navigate(action)
    }

}
