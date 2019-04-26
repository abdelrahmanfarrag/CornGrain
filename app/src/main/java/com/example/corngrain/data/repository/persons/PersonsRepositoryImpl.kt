package com.example.corngrain.data.repository.persons

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.outsource.TmdbNetworkLayer
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonsRepositoryImpl(private val networkSource: TmdbNetworkLayer) : PersonsRepository {
    override suspend fun getPersonsMovies(id: Int): LiveData<PersonMovies> {
        return withContext(Dispatchers.IO) {
            networkSource.loadPersonMovies(id)
            return@withContext networkSource.personMovies
        }
    }

    override suspend fun getPopularPersons(): LiveData<PopularPersons> {
        return withContext(Dispatchers.IO) {
            networkSource.loadPopularPersons()
            return@withContext networkSource.popularPersons
        }
    }

    override suspend fun getPopularPersonDetail(id: Int): LiveData<PersonDetail> {
        return withContext(Dispatchers.IO) {
            networkSource.loadPersonDetail(id)
            return@withContext networkSource.personDetail
        }
    }
}