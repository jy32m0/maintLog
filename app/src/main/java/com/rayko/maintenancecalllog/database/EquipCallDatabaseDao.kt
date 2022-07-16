package com.rayko.maintenancecalllog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface EquipCallDatabaseDao {

    @Insert
    fun insert(call: EquipCall)

    @Update
    fun update(call: EquipCall)

    @Query("SELECT * from call_log_table WHERE callId = :key")
    fun get(key: Long): EquipCall?

    @Query("DELETE from call_log_table")
    fun clear()

    @Query("SELECT * from call_log_table ORDER BY callId DESC")
    fun getAllCalls(): LiveData<List<EquipCall>>

    @Query("SELECT * from call_log_table ORDER BY callId DESC LIMIT 1")
    fun getLastCall(): EquipCall?
}