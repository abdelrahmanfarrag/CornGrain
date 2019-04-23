package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corngrain.data.repository.movies.TmdbRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val repository: TmdbRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(repository) as T
    }
}