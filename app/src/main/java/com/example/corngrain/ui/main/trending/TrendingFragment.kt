package com.example.corngrain.ui.main.trending

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.trending.SeriesAndTvShows
import com.example.corngrain.data.network.response.trending.Trending
import com.example.corngrain.data.repository.trending.TrendingRepository
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.trending.adapter.TrendingPagerAdapter
import com.example.corngrain.ui.main.trending.adapter.TrendingSeriesAdapter
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.android.synthetic.main.trending_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TrendingFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val factory by instance<TrendingFactory>()


    private lateinit var viewModel: TrendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trending_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(TrendingViewModel::class.java)
        bindMovieUI()
        bindTvShowsAndSeriesUI()
    }

    private fun bindMovieUI() = launch {
        val trendingMoviesJob = viewModel.fetchTrendingMovies.await()
        trendingMoviesJob.observe(this@TrendingFragment, Observer { result ->
            if (result == null) return@Observer
            views_container.visibility = View.VISIBLE
            loading_container.visibility = View.INVISIBLE
            val trendingMoviesAdapter = TrendingPagerAdapter(result.results)
            today_series_pager.adapter = trendingMoviesAdapter
            autoPagerSlide(today_series_pager, dots_layout, result.results.size, 5000)

        })
    }

    private fun bindTvShowsAndSeriesUI() = launch {
        val trendingSeries = viewModel.fetchTrendingSeries.await()
        trendingSeries.observe(this@TrendingFragment, Observer { result ->

            settingNormalRecyclerViewConfigs(
                context!!,
                result.results.toAdapterItems(),
                trending_series_list,
                RecyclerView.VERTICAL, true
                , 2
            ).setOnItemClickListener { item, view ->
                toDetailsScreen((item as TrendingSeriesAdapter).entries.id, view)
                Log.d("serieIdTest", "${item.entries.id} \n ${item.entries.name}")
            }

        })

    }

    private fun List<SeriesAndTvShows.Result>.toAdapterItems(): List<TrendingSeriesAdapter> {
        return this.map { item ->
            TrendingSeriesAdapter(item)
        }
    }

    private fun toDetailsScreen(id: Int, viewClicked: View) {
        val action = TrendingFragmentDirections.trendingSeriesAction(id)
        Navigation.findNavController(viewClicked).navigate(action)
    }

}
