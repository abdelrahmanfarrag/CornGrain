package com.example.corngrain.ui.main.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.corngrain.data.repository.di.TmdbRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val repository: TmdbRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(repository) as T
    }
}