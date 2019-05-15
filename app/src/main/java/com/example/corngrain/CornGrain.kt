package com.example.corngrain

import android.app.Application
import android.content.Context
import android.graphics.Movie
import com.example.corngrain.data.db.TmdbLocalDb
import com.example.corngrain.data.db.dao.movies.TopRatedDao
import com.example.corngrain.data.db.dao.series.OnAirDao
import com.example.corngrain.data.db.dao.series.PopularSerieDao
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.di.LoggingInterceptor
import com.example.corngrain.data.network.di.LoggingInterceptorImpl
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptorImpl
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.outsource.TmdbNetworkLayerImpl
import com.example.corngrain.data.repository.movies.TmdbRepository
import com.example.corngrain.data.repository.movies.TmdbRepositoryImpl
import com.example.corngrain.data.repository.persons.PersonsRepository
import com.example.corngrain.data.repository.persons.PersonsRepositoryImpl
import com.example.corngrain.data.repository.search.SearchRepository
import com.example.corngrain.data.repository.search.SearchRepositoryImpl
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.data.repository.series.SeriesRepositoryImpl
import com.example.corngrain.ui.main.movies.MovieViewModelFactory
import com.example.corngrain.ui.main.movies.details.MovieDetailViewModelFactory
import com.example.corngrain.ui.main.people.PeopleViewmodelFactory
import com.example.corngrain.ui.main.search.SearchViewModel
import com.example.corngrain.ui.main.search.SearchViewmodelFactory
import com.example.corngrain.ui.main.series.SeriesViewmodelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import kotlin.math.sin

//Adding all dependencies here through Kodein block
//make app class implement KodeinAware to make the entire app know that u r using DI to provide dependencies

@Suppress("unused", "RemoveExplicitTypeArguments")
class CornGrain : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CornGrain))
        bind() from singleton { TmdbLocalDb(instance<Context>()) }
        bind() from singleton { instance<TmdbLocalDb>().accessToPopularTable() }
        bind() from singleton { instance<TmdbLocalDb>().accessToUpcomingTable() }
        bind() from singleton { instance<TmdbLocalDb>().accessToTopRatedTable() }
        bind() from singleton { instance<TmdbLocalDb>().accessToPlayingTable() }
        bind() from singleton { instance<TmdbLocalDb>().accessToOnAirTodayTable() }
        bind() from singleton { instance<TmdbLocalDb>().accessToPopularSeriesEntity() }
        bind<NoConnectionInterceptor>() with singleton {
            NoConnectionInterceptorImpl(
                instance<Context>()
            )
        }
        bind<LoggingInterceptor>() with singleton { LoggingInterceptorImpl() }
        bind() from singleton {
            TmdbApi(
                instance<NoConnectionInterceptor>(),
                instance<LoggingInterceptor>()
            )
        }


        bind<TmdbNetworkLayer>() with singleton { TmdbNetworkLayerImpl(instance<TmdbApi>()) }
        bind<TmdbRepository>() with singleton {
            TmdbRepositoryImpl(
                instance<TmdbNetworkLayer>(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<SeriesRepository>() with singleton {
            SeriesRepositoryImpl(
                instance<TmdbNetworkLayer>(),
                instance<OnAirDao>(),
                instance<PopularSerieDao>()
            )
        }
        bind<SearchRepository>() with singleton {
            SearchRepositoryImpl(instance<TmdbNetworkLayer>())
        }
        bind<PersonsRepository>() with singleton {
            PersonsRepositoryImpl(instance<TmdbNetworkLayer>())
        }
        bind() from singleton { MovieViewModelFactory(instance<TmdbRepository>()) }
        bind() from singleton { SeriesViewmodelFactory(instance<SeriesRepository>()) }
        bind() from singleton { PeopleViewmodelFactory(instance<PersonsRepository>()) }
        bind() from singleton { SearchViewmodelFactory(instance<SearchRepository>()) }
        bind() from factory { id: Int ->
            MovieDetailViewModelFactory(
                id,
                instance<TmdbRepository>()
            )
        }
    }

}