package com.example.corngrain.ui.main.series.episodes

import androidx.lifecycle.ViewModel;
import com.example.corngrain.data.repository.series.SeriesRepository
import com.example.corngrain.utilities.lazyDeferred

class EpisodesViewModel(
    private val seasonId: Int,
    private val seasonNumber: Int, private val seriesRepository: SeriesRepository

) : ViewModel() {
    val seasonData by lazyDeferred {
        seriesRepository.getSerieSeason(seasonId, seasonNumber)
    }
}
