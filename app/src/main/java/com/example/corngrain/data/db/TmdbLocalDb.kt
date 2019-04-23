package com.example.corngrain.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.corngrain.data.db.dao.movies.PlayingDao
import com.example.corngrain.data.db.dao.movies.PopularDao
import com.example.corngrain.data.db.dao.movies.TopRatedDao
import com.example.corngrain.data.db.dao.movies.UpcomingDao
import com.example.corngrain.data.db.dao.series.OnAirDao
import com.example.corngrain.data.db.entity.movies.PlayingEntity
import com.example.corngrain.data.db.entity.movies.PopularEntity
import com.example.corngrain.data.db.entity.movies.TopRatedEntity
import com.example.corngrain.data.db.entity.movies.UpcomingEntity
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity

@Database(
    entities = [PopularEntity::class, UpcomingEntity::class, TopRatedEntity::class,
        PlayingEntity::class, OnAirTodayEntity::class],
    version = 1
)
abstract class TmdbLocalDb : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: TmdbLocalDb? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: constructDatabase(context).also { instance = it }
        }

        private fun constructDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TmdbLocalDb::class.java,
                "tmdb_local.db"
            ).build()

    }

    abstract fun accessToPopularTable(): PopularDao
    abstract fun accessToUpcomingTable(): UpcomingDao
    abstract fun accessToTopRatedTable(): TopRatedDao
    abstract fun accessToPlayingTable(): PlayingDao
    abstract fun accessToOnAirTodayTable(): OnAirDao

}