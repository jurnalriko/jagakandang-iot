package org.d3if43.jagakandang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if43.jagakandang.data.IotDao
import java.lang.IllegalArgumentException

class IotViewModelFactory(private val dataSource: IotDao) :
ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IotViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to contruct ViewModel")
    }
}