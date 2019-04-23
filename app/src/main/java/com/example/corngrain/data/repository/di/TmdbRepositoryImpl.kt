package com.example.corngrain.data.repository.di

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.dao.PopularDao
import com.example.corngrain.data.db.dao.TopRatedDao
import com.example.corngrain.data.db.dao.UpcomingDao
import com.example.corngrain.data.db.entity.PopularEntity
import com.example.corngrain.data.db.entity.TopRatedEntity
import com.example.corngrain.data.db.entity.UpcomingEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TmdbRepositoryImpl(
    private val networkSource: TmdbNetworkLayer,
    private val popularDao: PopularDao,
    private val upcomingDao: UpcomingDao,
    private val topRatedDao: TopRatedDao
) : TmdbRepository {


    init {
        networkSource.apply {
            latestMovies.observeForever { popular ->
                persistPopularMovies(popular.results)
            }
            upcomingMovies.observeForever { upcoming ->
                persistUpcomingMovies(upcoming.results)
            }
            topRatedMovies.observeForever { topRated ->
                persistingTopRatedMovies(topRated.results)
            }
        }
    }

    override suspend fun getPopularMovies(): List<PopularEntity> {
        return withContext(Dispatchers.IO) {
            getPopularMoviesFromNetworkCall()
            return@withContext popularDao.getMoviesInLocalDatabase()
        }
    }

    override suspend fun getUpcomingMovies(): List<UpcomingEntity> {

        return withContext(Dispatchers.IO) {
            getUpcomingMoviesFromNetworkCall()
            return@withContext upcomingDao.getPopularMovies()
        }
    }

    override suspend fun getTopRatedMovies(): List<TopRatedEntity> {
        return withContext(Dispatchers.IO) {
            getTopRatedMoviesFromNetworkCall()
            return@withContext topRatedDao.getTopRatedMovies()
        }
    }


    private fun persistPopularMovies(entries: List<PopularEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            popularDao.insertMovies(entries)
        }
    }

    private fun persistUpcomingMovies(entries: List<UpcomingEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            upcomingDao.insertingPopularMovies(entries)
        }

    }

    private fun persistingTopRatedMovies(entries: List<TopRatedEntity>) {
        GlobalScope.launch {
            topRatedDao.insertTopRatedMovies(entries)
        }
    }


    private suspend fun getPopularMoviesFromNetworkCall() {
        networkSource.loadLatestMovies()
    }

    private suspend fun getUpcomingMoviesFromNetworkCall() {
        networkSource.loadUpcomingMovies()
    }

    private suspend fun getTopRatedMoviesFromNetworkCall() {
        networkSource.loadTopRatedMovies()
    }


}
