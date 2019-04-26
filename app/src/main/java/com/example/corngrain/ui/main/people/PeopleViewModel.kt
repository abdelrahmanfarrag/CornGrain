package com.example.corngrain.ui.main.people

import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.repository.persons.PersonsRepository
import com.example.corngrain.utilities.lazyDeferred

class PeopleViewModel(personsRepository: PersonsRepository) : ViewModel() {

    val fetchPersons by lazyDeferred {
        personsRepository.getPopularPersons()
    }
}
