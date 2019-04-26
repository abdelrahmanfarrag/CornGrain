package com.example.corngrain.data.repository.persons

import androidx.lifecycle.LiveData
import com.example.corngrain.data.network.response.people.PopularPersons

interface PersonsRepository {
    suspend fun getPopularPersons():LiveData<PopularPersons>
}