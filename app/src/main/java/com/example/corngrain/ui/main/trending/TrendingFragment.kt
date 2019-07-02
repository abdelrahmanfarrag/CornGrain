package com.example.corngrain.ui.main.trending

import androidx.lifecycle.ViewModelProviders
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.ui.base.BaseFragment
import com.example.corngrain.ui.main.MainActivity
import com.example.corngrain.ui.main.trending.adapter.TrendingPagerAdapter
import com.example.corngrain.ui.main.trending.adapter.TrendingSeriesAdapter
import com.example.corngrain.utilities.autoSlideViewPager
import com.example.corngrain.utilities.gridRecyclerView
import com.example.corngrain.utilities.navigationDirectionAction
import com.example.corngrain.utilities.settingFragmentToolbar
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.trending_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TrendingFragment : BaseFragment() {


    override val kodein: Kodein by closestKodein()
    private val factory by instance<TrendingFactory>()

    private lateinit var viewModel: TrendingViewModel

    override fun setFragmentLayout(): Int = R.layout.trending_fragment

    override fun bindFragmentUI() {
        viewModel = ViewModelProviders.of(this, factory).get(TrendingViewModel::class.java)
        settingFragmentToolbar(context as MainActivity, R.string.bnv_trending, this)
        bindMovieUI()
        bindTvShowsAndSeriesUI(gridRecyclerViewRowCount)
    }


    private fun bindMovieUI() = launch {
        val trendingMoviesJob = viewModel.fetchTrendingMovies.await()
        trendingMoviesJob.observe(this@TrendingFragment, Observer { result ->

            if (result == null) return@Observer
            views_container.visibility = View.VISIBLE
            loading_container.visibility = View.INVISIBLE
            val trendingMoviesAdapter = TrendingPagerAdapter(result.results)
            today_series_pager.adapter = trendingMoviesAdapter
            val handler = Handler()
            autoSlideViewPager(today_series_pager, dots_layout, result.results.size, handler)
        })
    }

    private fun bindTvShowsAndSeriesUI(rowsCount: Int) = launch {
        val trendingSeries = viewModel.fetchTrendingSeries.await()
        trendingSeries.observe(this@TrendingFragment, Observer { result ->
            val adapter = TrendingSeriesAdapter(result.results[0])
            gridRecyclerView(
                context!!,
                adapter.toGroupeAdapterItems(result.results),
                RecyclerView.VERTICAL,
                rowsCount,
                trending_series_list
            ).setOnItemClickListener { item, view ->
                val action =
                    TrendingFragmentDirections.trendingSeriesAction((item as TrendingSeriesAdapter).entries.id)
                navigationDirectionAction(action, view)
            }

        })
    }

}
