package com.example.corngrain.ui.main.series

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.series.adapter.*
import com.example.corngrain.utilities.GlideApp
import com.example.corngrain.utilities.executeMoreClick
import kotlinx.android.synthetic.main.item_on_air.*
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.popular_series.*
import kotlinx.android.synthetic.main.rated_seasons.*
import kotlinx.android.synthetic.main.serie_detail_card.*
import kotlinx.android.synthetic.main.series_fragment.*
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
    }

    private fun bindUI() = launch {
        val onAir = viewModel.fetchOnAirToday.await()
        val popular = viewModel.fetchPopularSeries.await()
        val rated = viewModel.fetchTopRatedSeries.await()
        val inShow = viewModel.fetchInViewSeries.await()
        onAirTodaySection(onAir)
        popularSeriesSection(popular)
        val randomSerieDetail = viewModel.fetchDetails(movieId)
        detailCardUI(randomSerieDetail)
        topRatedSection(rated)
        inShowSection(inShow)
    }

    private fun onAirTodaySection(onAirData: LiveData<OnAirToday>) {
        onAirData.observe(this@Series, Observer { onAirJob ->
            // moreOnAirClick(job)
            if (onAirJob == null) return@Observer
            serie_group_loading.visibility = View.INVISIBLE
            serie_view_items.visibility = View.VISIBLE
            val pagerAdapter = OnAirTodayAdapter(onAirJob)
            today_series_pager.adapter = pagerAdapter
            autoPagerSlide(today_series_pager, dots_layout, pagerAdapter.count)
            series_on_air_more.setOnClickListener {
                val nextPage = onAirJob.page + 1
                currentPage = 0
                executeMoreClick(
                    nextPage,
                    onAirJob.totalPages
                ) { viewModel.loadOnAirToday(nextPage) }
            }
        })
    }

    private fun popularSeriesSection(popularSeries: LiveData<PopularSeries>) {
        popularSeries.observe(this@Series, Observer { seriesData ->
            val popularSeriesList = seriesData.results.toMutableList()
            GlideApp.with(context!!)
                .load(BASE_IMG_URL + seriesData.results[0].posterPath)
                .into(first_item_img)
            first_item_img.setOnClickListener {
                toSerieDetailScreen(seriesData.results[0].id, it)
            }
            popularSeriesList.removeAt(0)
            movieId = seriesData.results[generateRandomizedNumber()].id

            settingNormalRecyclerViewConfigs(
                this@Series.context,
                popularSeriesList.toAdapterItems(),
                popular_serie_list,
                RecyclerView.HORIZONTAL,
                true
            ).setOnItemClickListener { item, view ->
                toSerieDetailScreen((item as PopularSerieAdapter).entry.id, view)
            }
            popular_series_more.setOnClickListener {
                val nextPage = seriesData.page + 1
                executeMoreClick(nextPage, seriesData.totalPages) {
                    viewModel.loadPopularSeries(
                        nextPage
                    )
                }
            }
        })

    }

    private fun topRatedSection(topRated: LiveData<TopRatedSeries>) {
        topRated.observe(this@Series, Observer { data ->
            //moreRatedClick(topRatedSeries)
            settingNormalRecyclerViewConfigs(
                this@Series.context!!,
                data.results.toRatedSeriesItems(),
                rated_seasons_list,
                RecyclerView.HORIZONTAL
            ).setOnItemClickListener { item, view ->
                toSerieDetailScreen((item as RatedSeriesAdapter).entry.id, view)
            }
            top_rated_more.setOnClickListener {
                val nextPage = data.page + 1
                executeMoreClick(
                    nextPage,
                    data.totalPages
                ) { viewModel.loadTopRatedSeries(nextPage) }
            }
        })
    }

    private fun inShowSection(inShow: LiveData<SerieCurrentlyShowing>) {
        inShow.observe(this@Series, Observer { data ->
            //moreInshowClick(inshowSeries)
            val onAirPagerAdapter = OnAirAdapter(data.results)
            on_air_series_pager.adapter = onAirPagerAdapter
            autoPagerSlide(on_air_series_pager, dots_layout, data.results.size, 7000)
            inshow_more.setOnClickListener {
                val nextPage = data.page + 1
                executeMoreClick(nextPage, data.totalPages) { viewModel.loadOnAirToday(nextPage) }
            }
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
