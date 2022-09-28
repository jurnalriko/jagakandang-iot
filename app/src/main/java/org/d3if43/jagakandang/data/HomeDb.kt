package org.d3if43.jagakandang.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HomeDataClass::class], version = 1, exportSchema = false)
abstract class HomeDb : RoomDatabase() {

    abstract val dao : HomeDao

    companion object {
        @Volatile
        private var INSTANCE: HomeDb? = null

        fun getInstance(context: Context): HomeDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HomeDb::class.java,
                        "home.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}