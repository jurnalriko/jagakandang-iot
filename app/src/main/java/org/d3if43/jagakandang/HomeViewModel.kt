package org.d3if43.jagakandang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if43.jagakandang.data.HomeDao
import org.d3if43.jagakandang.data.HomeDataClass

class HomeViewModel(private val db: HomeDao) : ViewModel() {
    val data = db.getData()

    fun insertData(homeDataClass: HomeDataClass) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertData(homeDataClass)
            }
        }
    }
    fun updateData(homeDataClass: HomeDataClass) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.updateData(homeDataClass)
            }
        }
    }
    fun deleteData(ids: List<Int>) {
        val newIds = ids.toList()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.deleteData(newIds)
            }
        }
    }
}