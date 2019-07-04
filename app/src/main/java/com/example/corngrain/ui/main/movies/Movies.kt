package com.example.corngrain.ui.main.movies

import android.annotation.SuppressLint
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.network.response.movies.*
import com.example.corngrain.ui.base.BaseFragment
import com.example.corngrain.ui.main.MainActivity
import com.example.corngrain.ui.main.movies.adapters.*
import com.example.corngrain.utilities.*
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.now_playing_movies.*
import kotlinx.android.synthetic.main.popular_layout.*
import kotlinx.android.synthetic.main.rated_movies.*
import kotlinx.android.synthetic.main.upcoming_movies.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class Movies : BaseFragment(), KodeinAware, SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        Toast.makeText(context!!,query!!,Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override val kodein: Kodein by closestKodein()
    private val factory by instance<MovieViewModelFactory>()
    private lateinit var viewModel: MoviesViewModel


    override fun setFragmentLayout(): Int {
        return R.layout.movies_fragment
    }

    override fun bindFragmentUI() {
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)
        setHasOptionsMenu(true)
        buildUI()

    }

    override fun onResume() {
        super.onResume()
        (context as MainActivity).setToolbarTitle(resources.getString(R.string.bnv_movies))
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        /*    if (menu==null)return
            val item = menu.findItem(R.id.language_item) ?: return
            item.isVisible = false*/

    }

    @Suppress("ReplaceGetOrSet")
    private fun buildUI() = launch {
        upcomingMoviesSection(viewModel.upcomingMovies.await())
        playingMoviesSection(viewModel.playingMovies.await())
        ratedMoviesSection(viewModel.ratedMovies.await())
        popularMoviesSection(viewModel.popularMovies.await())
        /*search_card_container.setOnClickListener { card ->
            val actionToSearchScreen = MoviesDirections.actionSearchMovies()
            navigationDirectionAction(actionToSearchScreen, card)
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (inflater == null) return
        inflater.inflate(R.menu.movies_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.search_item -> {
                val menuItem = item.actionView as SearchView
                menuItem.setOnQueryTextListener(this)
                menuItem.queryHint= "Search..."
                /*   val actionWithValue =
                       MoviesDirections.actionSearchMovies()
                   navigationDirectionAction(actionWithValue,menuItem!!)*/
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun playingMoviesSection(playingMovies: LiveData<PlayingMovies>) {
        playingMovies.observe(this, Observer { playing ->
            if (playing == null) return@Observer
            val playingAdapter = PlayingAdapter(playing.results[0])
            loading_container.visibility = View.INVISIBLE
            view_container.visibility = View.VISIBLE
            val nextPage = playing.page + 1
            now_playing_more.setOnClickListener {
                executeMoreClick(
                    nextPage,
                    playing.totalPages
                ) { viewModel.loadMorePlayingMoviesAsync(nextPage) }
            }
            normalRecyclerView(
                this@Movies.context,
                playingAdapter.toGroupeAdapterItems(playing.results),
                LinearLayoutManager.HORIZONTAL
                , now_playing_movies_list
            ).setOnItemClickListener { item, view ->
                val playingClickedItem = (item as PlayingAdapter).entry.id
                val actionWithValue =
                    MoviesDirections.actionMoviesTabToMovieDetail(playingClickedItem)
                navigationDirectionAction(actionWithValue, view)
            }
        })
    }

    private fun upcomingMoviesSection(upcomingMovies: LiveData<UpcomingMovies>) {
        upcomingMovies.observe(this, Observer { upcomingData ->
            val upcomingAdapter = UpcomingAdapter(upcomingData.results[0])

            upcoming_more.setOnClickListener {
                val nextPage = upcomingData.page + 1
                executeMoreClick(
                    nextPage, upcomingData.totalPages
                ) { viewModel.loadMoreUpcomingMoviesAsync(nextPage) }
            }
            GlideApp.with(this@Movies.context!!)
                .load(com.example.corngrain.utilities.BASE_IMG_URL + upcomingData.results[0].posterPath)
                .into(upcoming_movies_first_img)
            upcoming_movies_first_img.setOnClickListener {
                val actionWithValue =
                    MoviesDirections.actionMoviesTabToMovieDetail(upcomingData.results[0].id)
                navigationDirectionAction(actionWithValue, it)
            }

            GlideApp.with(this@Movies.context!!)
                .load(com.example.corngrain.utilities.BASE_IMG_URL + upcomingData.results[1].posterPath)
                .into(upcoming_movies_second_img)
            upcoming_movies_second_img.setOnClickListener {
                val actionWithValue: NavDirections =
                    MoviesDirections.actionMoviesTabToMovieDetail(upcomingData.results[1].id)
                navigationDirectionAction(actionWithValue, it)

            }
            val list: MutableList<UpcomingMovies.Result> = upcomingData.results.toMutableList()
            list.subList(0, 2).clear()
            normalRecyclerView(
                this@Movies.context!!
                , upcomingAdapter.toGroupeAdapterItems(list),
                LinearLayoutManager.HORIZONTAL,
                upcoming_list
            ).setOnItemClickListener { item, view ->
                val upcomingId = (item as UpcomingAdapter).upcomingMovies.id
                val actionWithValue = MoviesDirections.actionMoviesTabToMovieDetail(upcomingId)
                navigationDirectionAction(actionWithValue, view)
            }
        })
    }

    private fun popularMoviesSection(popularMovies: LiveData<PopularMovies>) {
        popularMovies.observe(this, Observer { data ->
            val popularAdapter = PopularMoviesAdapter(data.results[0])
            popular_more.setOnClickListener {
                val nextPage = data.page + 1
                executeMoreClick(nextPage, data.totalPages) {
                    viewModel.loadMorePopularMoviesAsync(
                        nextPage
                    )
                }
            }
            normalRecyclerView(
                this.context!!,
                popularAdapter.toGroupeAdapterItems(data.results),
                RecyclerView.VERTICAL,
                popular_list
            ).setOnItemClickListener { item, view ->
                val popularClickedId = (item as PopularMoviesAdapter).entry.id
                val actionWithValue =
                    MoviesDirections.actionMoviesTabToMovieDetail(popularClickedId)
                navigationDirectionAction(actionWithValue, view)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun ratedMoviesSection(topRatedData: LiveData<TopRatedMovies>) {
        topRatedData.observe(this, Observer { data ->
            val topRatedAdapter = TopRatedAdapter(data.results[0])
            val generatedNumber = generateRandomizedNumber()
            GlideApp.with(this.context!!)
                .load(com.example.corngrain.utilities.BASE_IMG_URL + data.results[generatedNumber].backdropPath)
                .into(rated_movies_first_item)
            rated_movies_first_item.setOnClickListener {
                val actionWithValue: NavDirections =
                    MoviesDirections.actionMoviesTabToMovieDetail(data.results[generatedNumber].id)
                navigationDirectionAction(actionWithValue, it)
            }
            rated_movies_first_item_title.text = data.results[generatedNumber].title
            rated_movies_first_item_overview.setOnClickListener {
                val actionWithValue: NavDirections =
                    MoviesDirections.actionMoviesTabToMovieDetail(data.results[generatedNumber].id)
                navigationDirectionAction(actionWithValue, it)
            }


            rated_first_movie_bar.rating = data.results[generatedNumber].voteAverage.toFloat() / 2f
            rated_movies_first_item_overview.text = data.results[generatedNumber].overview
            val mutableList = data.results.toMutableList()
            mutableList.removeAt(generatedNumber)
            normalRecyclerView(
                this.context!!,
                topRatedAdapter.toGroupeAdapterItems(mutableList),
                RecyclerView.HORIZONTAL,
                movies_rated_list
            ).setOnItemClickListener { item, view ->
                val actionWithValue: NavDirections =
                    MoviesDirections.actionMoviesTabToMovieDetail((item as TopRatedAdapter).entry.id)
                navigationDirectionAction(actionWithValue, view)
            }
            rated_more.setOnClickListener {
                val nextPage = data.page + 1
                executeMoreClick(nextPage, data.totalPages) {
                    viewModel.loadMoreRatedMoviesAsync(
                        nextPage
                    )
                }
            }

            val position = generateRandomizedNumber()
            val title = data.results[position].title
            val content = data.results[position].overview
            settingSharedData(title, content)
        })
    }

    private fun settingSharedData(title: String, content: String) {
        val preferences = context?.getSharedPreferences(
            "local_notification_data",
            Context.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        editor?.putString("title", title)
        editor?.putString("content", content)
        editor?.putInt("time", hour)
        editor?.apply()
    }
}
