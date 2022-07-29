package com.rayko.maintenancecalllog.equipid

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rayko.maintenancecalllog.database.EquipCallDatabaseDao
import com.rayko.maintenancecalllog.log.LogViewModel

class EquipIdViewModelFactory  : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EquipIdViewModel::class.java)) {
                return EquipIdViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}