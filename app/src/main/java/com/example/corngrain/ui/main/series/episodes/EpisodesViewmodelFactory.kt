package com.example.corngrain.ui.main.series.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.corngrain.data.repository.series.SeriesRepository

class EpisodesViewmodelFactory(
    private val seasonId: Int,
    private val seasonNumber: Int
    ,
    private val seriesRepository: SeriesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EpisodesViewModel(seasonId, seasonNumber,seriesRepository) as T
    }

}