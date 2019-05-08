package com.example.corngrain.data.repository.movies

import androidx.lifecycle.LiveData
import com.example.corngrain.data.db.dao.movies.PlayingDao
import com.example.corngrain.data.db.dao.movies.PopularDao
import com.example.corngrain.data.db.dao.movies.TopRatedDao
import com.example.corngrain.data.db.dao.movies.UpcomingDao
import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.Detail
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.data.network.response.Videos
import com.example.corngrain.data.network.response.movies.*
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
    override suspend fun getTrailers(id: Int): LiveData<Videos> {
        return withContext(Dispatchers.IO) {
            networkSource.loadTrailers(id)
            return@withContext networkSource.videos
        }
    }

    override suspend fun getReviews(id: Int): LiveData<Reviews> {
        return withContext(Dispatchers.IO) {
            networkSource.loadReviews(id)
            return@withContext networkSource.reviews
        }
    }

    override suspend fun getMovieCast(id: Int): LiveData<MovieCredits> {
        return withContext(Dispatchers.IO) {
            networkSource.loadMovieCast(id)
            return@withContext networkSource.movieCast
        }
    }

    init {
        /*    networkSource.apply {
                popularMovies.observeForever { popular ->
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
            */
    }

    override suspend fun getPlayingMoviesFromResponse(): LiveData<PlayingMovies> {
        return withContext(Dispatchers.IO) {
            networkSource.loadPlayingMovies()
            return@withContext networkSource.playingMovies
        }
    }

    override suspend fun getUpcomingMovies(): LiveData<UpcomingMovies> {

        return withContext(Dispatchers.IO) {
            getUpcomingMoviesFromNetworkCall()
            return@withContext networkSource.upcomingMovies
        }
    }

    override suspend fun getTopRatedMovies(): LiveData<TopRatedMovies> {
        return withContext(Dispatchers.IO) {
            networkSource.loadTopRatedMovies()
            return@withContext networkSource.topRatedMovies
        }
    }

    override suspend fun getPopularMovies(): LiveData<PopularMovies> {
        return withContext(Dispatchers.IO) {
            getPopularMoviesFromNetworkCall()
            return@withContext networkSource.popularMovies
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

    override suspend fun getDetailedMovie(id: Int): LiveData<Detail> {
        return withContext(Dispatchers.IO) {
            networkSource.loadMovieDetail(id)
            return@withContext networkSource.movieDetail
        }
    }


    private suspend fun getPopularMoviesFromNetworkCall() {
        networkSource.loadPopularMovies()
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
