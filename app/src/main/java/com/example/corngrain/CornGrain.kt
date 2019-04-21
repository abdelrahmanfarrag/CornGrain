package com.example.corngrain

import android.app.Application
import android.content.Context
import com.example.corngrain.data.network.api.TmdbApi
import com.example.corngrain.data.network.di.LogginInterceptor
import com.example.corngrain.data.network.di.LogginInterceptorImpl
import com.example.corngrain.data.network.di.NoConnectionInterceptor
import com.example.corngrain.data.network.di.NoConnectionInterceptorImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

//Adding all dependencies here through Kodein block
//make app class implement KodeinAware to make the entire app know that u r using DI to provide dependencies

class CornGrain : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CornGrain))

        //1ST => ADDING RETROFIT DEPENDENCIES
        bind<NoConnectionInterceptor>() with singleton {
            NoConnectionInterceptorImpl(
                instance<Context>()
            )
        }
        bind<LogginInterceptor>() with singleton { LogginInterceptorImpl() }
        bind() from singleton {
            TmdbApi(
                instance<NoConnectionInterceptor>(),
                instance<LogginInterceptor>()
            )
        }
    }

}