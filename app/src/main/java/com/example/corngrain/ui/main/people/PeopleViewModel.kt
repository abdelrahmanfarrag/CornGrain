package com.example.corngrain.ui.main.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.repository.persons.PersonsRepository
import com.example.corngrain.utilities.lazyDeferred
import kotlinx.coroutines.Deferred

class PeopleViewModel(private val personsRepository: PersonsRepository) : ViewModel() {

    val fetchPersons by lazyDeferred {
        personsRepository.getPopularPersons()
    }

    suspend fun fetchPersonDetail(id: Int): LiveData<PersonDetail> {
        val details by lazyDeferred {
            personsRepository.getPopularPersonDetail(
                id
            )
        }
        return details.await()

    }
}
