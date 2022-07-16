package com.rayko.maintenancecalllog

import android.util.Log
import androidx.lifecycle.ViewModel

class EquipmentViewModel : ViewModel() {
    init {
        Log.i("EquipmentViewModel", "Model Created.")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("EquipmentViewModel", "EquipmentViewModel cleared")

    }
}