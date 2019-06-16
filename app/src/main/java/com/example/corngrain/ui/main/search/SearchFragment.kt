package com.example.corngrain.ui.main.search

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.network.response.search.MovieSearch
import com.example.corngrain.ui.base.BaseFragment
import com.example.corngrain.ui.main.search.adapter.SearchAdapter
import com.example.corngrain.utilities.navigationDirectionAction
import com.example.corngrain.utilities.normalRecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val factory by instance<SearchViewmodelFactory>()
    private lateinit var viewModel: SearchViewModel

    override fun setFragmentLayout(): Int = R.layout.search_fragment

    @SuppressLint("CheckResult")
    override fun bindFragmentUI() {
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        RxTextView.textChanges(search_text_et)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({ textChanges ->
                bindSearchListUI(textChanges.toString(), 1)

            }, { error ->
                Log.d("errorInflating", error.toString())

            })

    }

    @SuppressLint("SetTextI18n")
    private fun bindSearchListUI(query: String, page: Int) = launch {
        if (query.isEmpty()) {
            more_search_movies.visibility = View.INVISIBLE
            group_loading.visibility = View.VISIBLE
            no_result.visibility = View.INVISIBLE
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
                no_result.visibility = View.VISIBLE
                group_loading.visibility = View.INVISIBLE
                more_search_movies.visibility = View.INVISIBLE
                search_list.visibility = View.INVISIBLE
            } else {
                no_result.visibility = View.INVISIBLE
                more_search_movies.visibility = View.VISIBLE
                search_list.visibility = View.VISIBLE
                val searchAdapter = SearchAdapter(result.results[0])
                normalRecyclerView(
                    context!!,
                    searchAdapter.toGroupeAdapterItems(result.results),
                    RecyclerView.VERTICAL
                    , search_list
                ).setOnItemClickListener { item, view ->
                    (item as SearchAdapter).let { adapter ->
                        val actionToDetail =
                            SearchFragmentDirections.searchToDetail(adapter.entries.id)
                        navigationDirectionAction(actionToDetail, view)
                    }
                }
            }

        })

    }
}
