package org.d3if43.jagakandang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if43.jagakandang.data.IotDao
import org.d3if43.jagakandang.data.IotDataClass

class IotViewModel(private val db : IotDao) : ViewModel() {
    val data = db.getData()
    fun updateData(iotDataClass: IotDataClass){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.updateData(iotDataClass)
            }
        }
    }
    fun deleteData(ids : String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.deleteData(ids)
            }
        }
    }
    fun switchLogic1(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newChannel1 = iotDataClass.copy(channel_1 = "1")
        updateData(newChannel1)
    }
    fun switchLogic2(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newChannel1 = iotDataClass.copy(channel_1 = "0")
        updateData(newChannel1)
    }
    fun switchLogic3(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newChannel1 = iotDataClass.copy(channel_2 = "1")
        updateData(newChannel1)
    }
    fun switchLogic4(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newChannel1 = iotDataClass.copy(channel_2 = "0")
        updateData(newChannel1)
    }
    fun switchLogic5(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newChannel1 = iotDataClass.copy(wifi_config = "0")
        updateData(newChannel1)
    }
    fun switchLogic6(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newAutomationStatus = iotDataClass.copy(automation_status = "1")
        updateData(newAutomationStatus)
    }
    fun switchLogic7(ids : String, iotDataClass: IotDataClass){
        iotDataClass.id = ids
        val newAutomationStatus = iotDataClass.copy(automation_status = "0")
        updateData(newAutomationStatus)
    }
}