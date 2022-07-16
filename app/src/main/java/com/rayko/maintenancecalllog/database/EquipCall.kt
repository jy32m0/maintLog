package com.rayko.maintenancecalllog.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_log_table")
data class EquipCall(

    @PrimaryKey(autoGenerate = true)
    var callId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "equip_module")
    var equipModule: String = "General",

    @ColumnInfo(name = "call_reason")
    var reasonCall: String? = "Unspecified",

    @ColumnInfo(name = "call_remedy")
    var remedyCall: String? = "Unspecified"
)