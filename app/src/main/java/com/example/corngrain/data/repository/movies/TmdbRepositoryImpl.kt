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
import com.example.corngrain.data.network.response.*
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
    override suspend fun getSimilarMovies(id: Int): LiveData<Similar> {
        return withContext(Dispatchers.IO) {
            networkSource.loadSimilarMovies(id)
            return@withContext networkSource.similar
        }
    }

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

    override suspend fun getMovieCast(id: Int): LiveData<Credits> {
        return withContext(Dispatchers.IO) {
            networkSource.loadMovieCast(id)
            return@withContext networkSource.cast
        }
    }


    override suspend fun getPlayingMoviesFromResponse(page:Int): LiveData<PlayingMovies> {
        return withContext(Dispatchers.IO) {
            networkSource.loadPlayingMovies(page)
            return@withContext networkSource.playingMovies
        }
    }

    override suspend fun getUpcomingMovies(page: Int): LiveData<UpcomingMovies> {

        return withContext(Dispatchers.IO) {
            getUpcomingMoviesFromNetworkCall(page)
            return@withContext networkSource.upcomingMovies
        }
    }

    override suspend fun getTopRatedMovies(page: Int): LiveData<TopRatedMovies> {
        return withContext(Dispatchers.IO) {
            networkSource.loadTopRatedMovies(page)
            return@withContext networkSource.topRatedMovies
        }
    }

    override suspend fun getPopularMovies(page: Int): LiveData<PopularMovies> {
        return withContext(Dispatchers.IO) {
            getPopularMoviesFromNetworkCall(page)
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


    private suspend fun getPopularMoviesFromNetworkCall(page: Int) {
        networkSource.loadPopularMovies(page)
    }

    private suspend fun getUpcomingMoviesFromNetworkCall(page: Int) {
        networkSource.loadUpcomingMovies(page)
    }

    private suspend fun getTopRatedMoviesFromNetworkCall() {
   //     networkSource.loadTopRatedMovies()
    }

    private suspend fun getNowPlayingMoviesFromNetworkCall() {
        //networkSource.loadPlayingMovies()
    }

}
