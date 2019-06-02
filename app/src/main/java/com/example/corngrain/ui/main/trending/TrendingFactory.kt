package com.example.corngrain.ui.main.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corngrain.data.repository.trending.TrendingRepository

class TrendingFactory(val repository: TrendingRepository):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return (TrendingViewModel(repository) as T)
    }
}