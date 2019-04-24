package com.example.corngrain.ui.main.series

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.data.network.response.series.SerieDetail
import com.example.corngrain.data.network.response.series.TopRatedSeries
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.series.adapter.OnAirTodayAdapter
import com.example.corngrain.ui.main.series.adapter.PopularSerieAdapter
import com.example.corngrain.ui.main.series.adapter.RatedSeriesAdapter
import com.example.corngrain.ui.main.series.adapter.SeasonsAdapter
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.popular_series.*
import kotlinx.android.synthetic.main.rated_seasons.*
import kotlinx.android.synthetic.main.serie_detail_card.*
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

    var currentPage: Int = 0
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
        val job = viewModel.fetchSeries.await()
        //Log.d("seasons", job.size.toString())
        val pagerAdapter = OnAirTodayAdapter(job)
        if (today_series_pager != null) {
            today_series_pager.adapter = pagerAdapter
        }
        dots_layout.count = job.size
        pagerToAutoNext(pagerAdapter.count)
        val popularSeries = viewModel.fetchPopularSeries.await()
        GlideApp.with(context!!)
            .load(BASE_IMG_URL + popularSeries.get(0).posterPath)
            .into(first_item_img)
        popularSeries.removeAt(0)
        initPopularRecycler(popularSeries.toAdapterItems())

        val randomEntryToLoad = job[generateRandomizedNumber()].id
        val randomSerieDetail = viewModel.fetchDetails(randomEntryToLoad)
        detailCardUI(randomSerieDetail)

        val topRatedSeries = viewModel.fetchTopRatedSeries.await()
        initToRatedSeasonRecycler(topRatedSeries.value?.results?.toRatedSeriesItems()!!)

    }

    private fun generateRandomizedNumber(): Int {
        return (0..19).random()
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

    private fun detailCardUI(data: LiveData<SerieDetail>) {
        serie_detail_title.text = data.value?.originalName
        serie_detail_overview.text = data.value?.overview
        serie_detail_rating.text = "Rating \n ${data.value?.voteAverage}"
        serie_detail_episodes.text = "Episodes \n ${data.value?.numberOfEpisodes}"
        serie_detail_seasons.text = "Seasons \n ${data.value?.numberOfSeasons}"
        season_genres.text = setGenres(data.value?.genres!!).toString()
        seasons_info.setOnClickListener {
            val uri = Uri.parse(data.value?.homepage)
            val intents = Intent(Intent.ACTION_VIEW, uri)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            context?.startActivity(intents)


        }
        GlideApp.with(this.context!!)
            .load(BASE_IMG_URL + data.value?.posterPath)
            .into(serie_detail_poster)
        GlideApp.with(this.context!!)
            .load(BASE_IMG_URL + data.value?.backdropPath)
            .into(serie_detail_backdrop)


        initSeasonsRecycler(data.value?.seasons?.toSeasonDetail()!!)
    }


    private fun List<PopularSeriesEntity>.toAdapterItems(): List<PopularSerieAdapter> {
        return this.map { item ->
            PopularSerieAdapter(item)
        }
    }

    private fun initSeasonsRecycler(entries: List<SeasonsAdapter>) {
        val groupie = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        seasons_list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupie
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

    private fun initPopularRecycler(entries: List<PopularSerieAdapter>) {
        val groupie = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        popular_serie_list.apply {
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

            adapter = groupie
        }
    }


    private fun initToRatedSeasonRecycler(entries: List<RatedSeriesAdapter>) {
        val group = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        rated_seasons_list.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = group
        }
    }


    private fun pagerToAutoNext(items: Int) {
        today_series_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                dots_layout.setSelected(position)
            }

        })
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                if (currentPage!! < items) {
                    if (today_series_pager != null) {
                        today_series_pager.setCurrentItem(currentPage!!, true)
                        handler.postDelayed(this, 3000)
                        currentPage++
                    }
                } else if (currentPage == items) {
                    currentPage = 0
                    if (today_series_pager != null) {
                        today_series_pager.setCurrentItem(currentPage, true)
                        handler.postDelayed(this, 3000)
                        currentPage++
                    }
                }
            }
        })

    }
}
