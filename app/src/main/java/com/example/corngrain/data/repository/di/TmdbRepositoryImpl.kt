package com.example.corngrain.data.repository.di

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.dao.PopularDao
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TmdbRepositoryImpl(
    private val networkSource: TmdbNetworkLayer,
    private val popularDao: PopularDao
) : TmdbRepository {


    init {
        networkSource.apply {
            latestMovies.observeForever {popular ->
                Log.d("catchedData",latestMovies.value?.totalPages.toString())
                persistPopularMovies(popular.results)

            }
        }
    }
    override suspend fun getPopularMovies(): List<PopularEntity>{
        return withContext(Dispatchers.IO) {
            getPopularMoviesFromNetworkCall()
            return@withContext popularDao.getMoviesInLocalDatabase()
        }
    }

    private fun persistPopularMovies(entries: List<PopularEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            popularDao.insertMovies(entries)
            Log.d("inserting",popularDao.getMoviesInLocalDatabase().size.toString())
        }
    }


    private suspend fun getPopularMoviesFromNetworkCall() {
        networkSource.loadLatestMovies()
    }


}
