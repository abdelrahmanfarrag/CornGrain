package com.example.corngrain.ui.main.series

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager

import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.PopularSeriesEntity
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.series.adapter.OnAirTodayAdapter
import com.example.corngrain.ui.main.series.adapter.PopularSerieAdapter
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.popular_series.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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
        today_series_pager.adapter = pagerAdapter
        dots_layout.count = job.size
        pagerToAutoNext(pagerAdapter.count)
        val popularSeries = viewModel.fetchPopularSeries.await()
        GlideApp.with(context!!)
            .load(BASE_IMG_URL + popularSeries.get(0).posterPath)
            .into(first_item_img)
        popularSeries.removeAt(0)
        initPopularRecycler(popularSeries.toAdapterItems())

    }

    private fun List<PopularSeriesEntity>.toAdapterItems(): List<PopularSerieAdapter> {
        return this.map { item ->
            PopularSerieAdapter(item)
        }
    }

    private fun initPopularRecycler(entries: List<PopularSerieAdapter>) {
        val groupie = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        popular_serie_list.apply {
            layoutManager =
                GridLayoutManager(context, 2,GridLayoutManager.HORIZONTAL,false)

            adapter = groupie
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
