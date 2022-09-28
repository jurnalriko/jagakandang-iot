package org.d3if43.jagakandang.data

import androidx.lifecycle.LiveData

interface IotDao {
    fun getData(): LiveData<List<IotDataClass>>
    fun updateData(iotDataClass: IotDataClass)
    fun deleteData(ids: String)
}