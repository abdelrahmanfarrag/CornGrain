package com.example.corngrain.ui.main.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corngrain.data.repository.search.SearchRepository

class SearchViewmodelFactory(private val searchRepository: SearchRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchRepository) as T
    }
}