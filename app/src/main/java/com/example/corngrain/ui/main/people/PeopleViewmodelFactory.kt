package com.example.corngrain.ui.main.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.corngrain.data.repository.persons.PersonsRepository

@Suppress("UNCHECKED_CAST")
class PeopleViewmodelFactory(private val personsRepository: PersonsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleViewModel(personsRepository) as T
    }
}