package com.example.corngrain.data.repository.persons

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons

interface PersonsRepository {
    suspend fun getPopularPersons(): LiveData<PopularPersons>
    suspend fun getPopularPersonDetail(id: Int): LiveData<PersonDetail>
    suspend fun getPersonsMovies(id: Int): LiveData<PersonMovies>
}