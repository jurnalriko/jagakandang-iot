package org.d3if43.jagakandang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if43.jagakandang.data.HomeDao

class HomeViewModelFactory(private val dataSource: HomeDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}