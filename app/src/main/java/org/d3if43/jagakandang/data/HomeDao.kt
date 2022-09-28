package org.d3if43.jagakandang.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HomeDao {
    @Insert
    fun insertData(homeDataClass: HomeDataClass)

    @Query("SELECT * FROM homedataclass ORDER BY id")
    fun getData() : LiveData<List<HomeDataClass>>

    @Update
    fun updateData(homeDataClass: HomeDataClass)

    @Query("DELETE FROM homedataclass WHERE id IN (:ids)")
    fun deleteData(ids: List<Int>)
}