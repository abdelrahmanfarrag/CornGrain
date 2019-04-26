package com.example.corngrain.data.repository.persons

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.people.PopularPersons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonsRepositoryImpl(private val networkSource: TmdbNetworkLayer) : PersonsRepository {


    override suspend fun getPopularPersons(): LiveData<PopularPersons> {
        return withContext(Dispatchers.IO) {
            networkSource.loadPopularPersons()
            return@withContext networkSource.popularPersons
        }
    }
}