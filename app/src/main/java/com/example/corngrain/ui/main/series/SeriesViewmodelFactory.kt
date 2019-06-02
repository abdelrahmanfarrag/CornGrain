package com.example.corngrain.ui.main.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corngrain.data.repository.series.SeriesRepository

@Suppress("UNCHECKED_CAST")
class SeriesViewmodelFactory(val seriesRepository: SeriesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return SeriesViewModel(seriesRepository) as T
    }
}