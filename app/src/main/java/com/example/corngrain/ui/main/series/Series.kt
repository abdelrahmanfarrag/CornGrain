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
        retainInstance = true// <--------- the fragment retain his configuration
        bindUI()
        bindUI2()
    }

    private fun bindUI() = launch {
        val job = viewModel.fetchSeries.await()
        job.observeForever { onAirJob ->
            val pagerAdapter = OnAirTodayAdapter(onAirJob)
            if (today_series_pager != null) {
                today_series_pager.adapter = pagerAdapter
            }
            movieId = onAirJob.results[generateRandomizedNumber()].id
            autoPagerSlide(today_series_pager, dots_layout, pagerAdapter.count)
        }

        val popularSeries = viewModel.fetchPopularSeries.await()
        Log.d("itemsOfSeries", popularSeries.size.toString())
        if (first_item_img != null)
            GlideApp.with(context!!)
                .load(BASE_IMG_URL + popularSeries[0].posterPath)
                .into(first_item_img)
        popularSeries.removeAt(0)
        (popular_serie_list != null)
        settingNormalRecyclerViewConfigs(
            this@Series.context,
            popularSeries.toAdapterItems(),
            popular_serie_list,
            RecyclerView.HORIZONTAL,
            true
        )


        val randomSerieDetail = viewModel.fetchDetails(movieId)
        detailCardUI(randomSerieDetail)

        val topRatedSeries = viewModel.fetchTopRatedSeries.await()

        topRatedSeries.observeForever {
            if (rated_seasons_list != null)
                settingNormalRecyclerViewConfigs(
                    this@Series.context!!,
                    it.results.toRatedSeriesItems(),
                    rated_seasons_list,
                    RecyclerView.HORIZONTAL
                )
        }

    }

    private fun bindUI2() = launch(Dispatchers.Main) {
        val inshowSeries = viewModel.fetchInViewSeries.await()
        inshowSeries.observeForever { data ->
            val onAirPagerAdapter = OnAirAdapter(data.results)
            if (on_air_series_pager != null)
                on_air_series_pager.adapter = onAirPagerAdapter
            autoPagerSlide(on_air_series_pager, dots_layout, data.results.size)


        }
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
        data.observeForever {
            serie_detail_title?.text = it.originalName
            serie_detail_overview?.text = it.overview
            serie_detail_rating?.text = "Rating \n ${it.voteAverage}"
            serie_detail_episodes?.text = "Episodes \n ${it.numberOfEpisodes}"
            serie_detail_seasons?.text = "Seasons \n ${it.numberOfSeasons}"
            season_genres?.text = setGenres(it.genres).toString()
            //initSeasonsRecycler(it.seasons.toSeasonDetail())
            settingNormalRecyclerViewConfigs(
                this.context, it.seasons.toSeasonDetail(), seasons_list, RecyclerView
                    .HORIZONTAL
            )

        }

        if (serie_detail_poster != null)
            GlideApp.with(this.context!!)
                .load(BASE_IMG_URL + data.value?.posterPath)
                .into(serie_detail_poster)
        if (serie_detail_backdrop != null)
            GlideApp.with(this.context!!)
                .load(BASE_IMG_URL + data.value?.backdropPath)
                .into(serie_detail_backdrop)
        if (seasons_info != null)
            seasons_info.setOnClickListener {
                val uri = Uri.parse(data.value?.homepage)
                val intents = Intent(Intent.ACTION_VIEW, uri)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                context?.startActivity(intents)


            }

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


}
