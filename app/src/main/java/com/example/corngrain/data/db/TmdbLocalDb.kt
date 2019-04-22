package com.example.corngrain.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.corngrain.data.db.dao.PopularDao
import com.example.corngrain.data.db.entity.PopularEntity

@Database(entities = [PopularEntity::class], version = 1)
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

    abstract fun accessToPopularDatabase(): PopularDao
}