package com.example.corngrain.ui.main.search

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.search.adapter.SearchAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.plugins.RxAndroidPlugins
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class SearchFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val factory by instance<SearchViewmodelFactory>()

    private lateinit var viewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        RxTextView.textChanges(search_text_et)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({ textChanges ->
                bindSearchListUI(textChanges.toString(), 1)

            }, { error ->
                Log.d("errorInflating", error.toString())

            })


    }

    private fun bindSearchListUI(query: String, page: Int) = launch {
        if (query.isEmpty()) {
            more_search_movies.visibility = View.INVISIBLE
            group_loading.visibility = View.VISIBLE
            no_result.visibility=View.INVISIBLE
            textView_loading.text = "Waiting for your type"
            search_list.adapter = null
        } else {
            val search = viewModel.getSearchedMoviesAsync(query, page)
            observeData(query, search)
        }

    }


    private fun observeData(query: String, movieResult: LiveData<MovieSearch>) {
        movieResult.observe(this, Observer { result ->
            if (result == null) return@Observer
            group_loading.visibility = View.INVISIBLE
            more_search_movies.visibility = View.VISIBLE
            var currentPage = result.page
            val totalPages = result.totalPages
            more_search_movies.setOnClickListener {
                launch {
                    if (currentPage < totalPages) {
                        currentPage += 1
                        viewModel.getSearchedMoviesAsync(query, currentPage)
                    }
                }
            }
            if (result.results.isEmpty()) {
                Log.d("empty","Result isempty")
                no_result.visibility = View.VISIBLE
                group_loading.visibility=View.INVISIBLE
                more_search_movies.visibility = View.INVISIBLE
                search_list.visibility = View.INVISIBLE
            } else {
                no_result.visibility = View.INVISIBLE
                more_search_movies.visibility = View.VISIBLE
                search_list.visibility = View.VISIBLE
                settingNormalRecyclerViewConfigs(
                    context!!,
                    result.results.toAdapterItems(),
                    search_list,
                    RecyclerView.VERTICAL
                ).setOnItemClickListener { item, view ->
                    (item as SearchAdapter).let { adapter ->
                        toDetailScreen(adapter.entries.id, view)
                    }
                }
            }

            })

    }


    private fun List<MovieSearch.Result>.toAdapterItems(): List<SearchAdapter> {
        return this.map { item ->
            SearchAdapter(item)
        }
    }

    private fun toDetailScreen(id: Int, viewClicked: View) {
        val actionToDetail = SearchFragmentDirections.searchToDetail(id)
        Navigation.findNavController(viewClicked).navigate(actionToDetail)
    }


}
