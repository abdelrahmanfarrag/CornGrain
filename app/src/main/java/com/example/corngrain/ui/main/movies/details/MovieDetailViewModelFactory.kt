package com.example.corngrain.ui.main.movies.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.corngrain.data.repository.movies.TmdbRepository

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(private val id: Int, private val repository: TmdbRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(id, repository) as T
    }
}