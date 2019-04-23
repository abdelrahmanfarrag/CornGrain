package com.example.corngrain.data.repository.movies

import com.example.corngrain.data.db.dao.movies.PlayingDao
import com.example.corngrain.data.db.dao.movies.PopularDao
import com.example.corngrain.data.db.dao.movies.TopRatedDao
import com.example.corngrain.data.db.dao.movies.UpcomingDao
import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TmdbRepositoryImpl(
    private val networkSource: TmdbNetworkLayer,
    private val popularDao: PopularDao,
    private val upcomingDao: UpcomingDao,
    private val topRatedDao: TopRatedDao,
    private val playingDao: PlayingDao
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
            playingMovies.observeForever { playing ->
                persistingNowPlayingMovies(playing.results)

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

    override suspend fun getPlayingMovies(): List<PlayingEntity> {
        return withContext(Dispatchers.IO) {
            getNowPlayingMoviesFromNetworkCall()
            return@withContext playingDao.getNowPlayingMovies()
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

    private fun persistingNowPlayingMovies(entries: List<PlayingEntity>) {
        GlobalScope.launch {
            playingDao.insertNowPlayingMovies(entries)
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

    private suspend fun getNowPlayingMoviesFromNetworkCall() {
        networkSource.loadPlayingMovies()
    }

}
