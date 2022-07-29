package com.rayko.maintenancecalllog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// exportSchema=true to save old versions
@Database(entities = [EquipCall::class], version = 1, exportSchema = true)
abstract class EquipCallDatabase : RoomDatabase() {

    abstract val equipCallDatabaseDao: EquipCallDatabaseDao

    // allow clients to access the method for creating or getting the database
    // without instantiating the class
    companion object {

        @Volatile
        private var INSTANCE: EquipCallDatabase? = null

        // use database builder
        fun getInstance(context: Context) : EquipCallDatabase {
            synchronized(this) {
                var instance = INSTANCE

                // check if any existing database instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EquipCallDatabase::class.java,
                        "call_log_database"
                    )
                        .fallbackToDestructiveMigration() //start up fresh instead of migration
                        .build()

                    // INSTANCE to newly created database
                    INSTANCE = instance
                }

                return instance
            }
        }

    }
}