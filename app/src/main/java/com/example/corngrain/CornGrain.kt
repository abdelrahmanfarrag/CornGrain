package com.example.corngrain

import android.app.Application
import android.content.Context
import com.example.corngrain.data.db.TmdbLocalDb
import com.example.corngrain.data.db.dao.movies.TopRatedDao
import com.example.corngrain.data.db.dao.series.OnAirDao
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.di.LoggingInterceptor
import com.example.corngrain.data.network.di.LoggingInterceptorImpl
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptorImpl
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.outsource.TmdbNetworkLayerImpl
import com.example.corngrain.data.repository.movies.TmdbRepository
import com.example.corngrain.data.repository.movies.TmdbRepositoryImpl
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.data.repository.series.SeriesRepositoryImpl
import com.example.corngrain.ui.main.movies.MovieViewModelFactory
import com.example.corngrain.ui.main.series.SeriesViewmodelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
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
                instance<OnAirDao>()
            )
        }
        bind() from provider { MovieViewModelFactory(instance<TmdbRepository>()) }
        bind() from provider { SeriesViewmodelFactory(instance<SeriesRepository>()) }
    }

}