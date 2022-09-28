package org.d3if43.jagakandang.data

import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class IotDb private constructor() {

    private val database = FirebaseDatabase.getInstance().getReference(PATH)

    val dao = object : IotDao {
        override fun getData(): LiveData<List<IotDataClass>> {
            return IotLiveData(database)
        }
        override fun updateData(iotDataClass: IotDataClass) {
            database.child(iotDataClass.id).setValue(iotDataClass)
        }

        override fun deleteData(ids: String) {
            database.child(ids).removeValue()
        }


    }
    companion object {
        val user = Firebase.auth.currentUser

        private val PATH = "IoT/" + user!!.uid
        @Volatile
        private var INSTANCE: IotDb? = null
        fun getInstance(): IotDb {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = IotDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}