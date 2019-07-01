package com.example.corngrain.ui.main.series

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.*
import com.example.corngrain.ui.base.BaseFragment
import com.example.corngrain.ui.main.series.adapter.*
import com.example.corngrain.utilities.*
import kotlinx.android.synthetic.main.item_on_air.*
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.popular_series.*
import kotlinx.android.synthetic.main.rated_seasons.*
import kotlinx.android.synthetic.main.serie_detail_card.*
import kotlinx.android.synthetic.main.series_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import java.lang.StringBuilder

class Series : BaseFragment() {


    private val factory by instance<SeriesViewmodelFactory>()
    private lateinit var viewModel: SeriesViewModel

    private var serieId: Int = 0
    private var currentPage = 0


    override fun setFragmentLayout(): Int = R.layout.series_fragment

    override fun bindFragmentUI() {
        viewModel = ViewModelProviders.of(this, factory).get(SeriesViewModel::class.java)
        bindUI()


    }

    override fun onResume() {
        super.onResume()
        settingFragmentToolbar(context!!, R.string.bnv_tv, this)
    }


    private fun bindUI() = launch {
        val onAir = viewModel.fetchOnAirToday.await()
        val popular = viewModel.fetchPopularSeries.await()
        val rated = viewModel.fetchTopRatedSeries.await()
        val inShow = viewModel.fetchInViewSeries.await()
        onAirTodaySection(onAir)
        popularSeriesSection(popular)
        val randomSerieDetail = viewModel.fetchDetails(serieId)
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
            val handler = Handler()

            autoSlideViewPager(today_series_pager, dots_layout, pagerAdapter.count, handler)
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
                val toDetailScreen =
                    SeriesDirections.serieDetailAction(seriesData.results[0].id)
                navigationDirectionAction(toDetailScreen, it)
            }
            popularSeriesList.removeAt(0)
            serieId = seriesData.results[generateRandomizedNumber()].id
            val popularAdapter = PopularSerieAdapter(seriesData.results[0])
            gridRecyclerView(
                this@Series.context,
                popularAdapter.toGroupeAdapterItems(popularSeriesList),
                RecyclerView.HORIZONTAL, 2, popular_serie_list

            ).setOnItemClickListener { item, view ->
                val toDetailScreen =
                    SeriesDirections.serieDetailAction((item as PopularSerieAdapter).entry.id)
                navigationDirectionAction(toDetailScreen, view)
            }
            popular_series_more.setOnClickListener {
                val nextPage = seriesData.page + 1
                launch {
                    serieId = seriesData.results[generateRandomizedNumber()].id
                    val randomSerieDetail = viewModel.fetchDetails(serieId)
                    detailCardUI(randomSerieDetail)
                }
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
            val ratedAdapter = RatedSeriesAdapter(data.results[0])
            normalRecyclerView(
                this@Series.context!!,
                ratedAdapter.toGroupeAdapterItems(data.results),
                RecyclerView.HORIZONTAL
                , rated_seasons_list
            ).setOnItemClickListener { item, view ->
                val toDetailScreen =
                    SeriesDirections.serieDetailAction((item as RatedSeriesAdapter).entry.id)
                navigationDirectionAction(toDetailScreen, view)
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
            val firstImage = data.results[0].posterPath
            val secondImage = data.results[11].posterPath
            val inshowFirstRecyclerAdapter = InshowAdapter(data.results[1])
            val inshowSecondRecyclerAdapter = InshowAdapter(data.results[12])
            GlideApp.with(this)
                .load(BASE_IMG_URL + firstImage)
                .into(first_inshow_img)

            GlideApp.with(this)
                .load(BASE_IMG_URL + secondImage)
                .into(second_inshow_img)
            first_inshow_img.setOnClickListener {
                val toDetailScreen =
                    SeriesDirections.serieDetailAction(data.results[0].id)
                navigationDirectionAction(toDetailScreen, it)

            }
            second_inshow_img.setOnClickListener {
                val toDetailScreen =
                    SeriesDirections.serieDetailAction(data.results[11].id)
                navigationDirectionAction(toDetailScreen, it)
            }

            val mutableInshowList = data.results.toMutableList()
            val firstList = mutableInshowList.subList(1, 10)
            val secondList = mutableInshowList.subList(12, 19)
            gridRecyclerView(
                context!!,
                inshowFirstRecyclerAdapter.toGroupeAdapterItems(firstList),
                RecyclerView.HORIZONTAL,
                2,
                first_inshow_list
            ).setOnItemClickListener { item, view ->
                val toDetailScreen =
                    SeriesDirections.serieDetailAction((item as InshowAdapter).data.id)
                navigationDirectionAction(toDetailScreen, view)

            }
            gridRecyclerView(
                context!!,
                inshowSecondRecyclerAdapter.toGroupeAdapterItems(secondList),
                RecyclerView.HORIZONTAL,
                2,
                second_inshow_list
            ).setOnItemClickListener { item, view ->
                val toDetailScreen =
                    SeriesDirections.serieDetailAction((item as InshowAdapter).data.id)
                navigationDirectionAction(toDetailScreen, view)

            }


            inshow_more.setOnClickListener {
                val nextPage = data.page + 1
                executeMoreClick(nextPage, data.totalPages) { viewModel.loadInViewSeries(nextPage) }
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
            val seasonsAdapter = SeasonsAdapter(it.seasons[0])
            season_genres?.text = setGenres(it.genres).toString()
            normalRecyclerView(
                this.context,
                seasonsAdapter.toGroupeAdapterItems(it.seasons),
                RecyclerView.HORIZONTAL,
                seasons_list
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


}
