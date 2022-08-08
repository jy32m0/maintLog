package com.rayko.maintenancecalllog.log

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rayko.maintenancecalllog.database.EquipCallDatabaseDao
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.rayko.maintenancecalllog.callReason
import com.rayko.maintenancecalllog.callSolution
import com.rayko.maintenancecalllog.database.EquipCall
import com.rayko.maintenancecalllog.formatCalls
import kotlinx.coroutines.*


class LogViewModel (val database: EquipCallDatabaseDao, application: Application)
        : AndroidViewModel(application) {

        private val context =
                getApplication<Application>().applicationContext

        // coroutine 1: create viewModelJob and override onCleared to finish coroutines at the end
        private var viewModelJob = Job()

        // coroutine 1: cancel all coroutines when this viewModel is destroyed.
        override fun onCleared() {
                super.onCleared()
                viewModelJob.cancel()
                Log.i("LogViewModel", "debug: 30 - onCleared")
        }

        // cr 2: define a scope for the coroutines to run
        private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        // cr 3: create live data var and use a coroutine to initialize it from the database
        private var currentCall = MutableLiveData<EquipCall?>()

        // cr 4: get all calls from the database
        private val calls = database.getAllCalls()

        val logResult = Transformations.map(calls) { calls ->
                formatCalls(calls, application.resources)

        }

        // cr 5: initialize currentCall with a local fun in init block
        init {
                initializeCurrentCall()
        }

        // cr 5: implement the function and launch a coroutine in the uiScope
        private fun initializeCurrentCall() {
                uiScope.launch {
                        currentCall.value = currentCallFromDatabase() }
        }

        // cr 5: Implement the suspend function and return nullable if no currentCall started.
        // Return the result from a coroutine that runs in the Dispatchers.IO context.
        private suspend fun currentCallFromDatabase(): EquipCall? {
                return withContext(Dispatchers.IO) {
                        var call = database.getLastCall()
                        if (call?.endTimeMilli != call?.startTimeMilli) {
                                call = null
                        }
                        call
                }
        }

        // ** currentCall's start timer should start when logFrag is created from EquipIdFrag.

        // cr 6: Implement click handler function
        // to create a new EquipCall, insert it to the database, and assign it to currentCall.
        // From this point, it will be time-consuming. So, coroutine will be launched.
        fun onStartTracking(equipModule: String) {
                // empty, so they won't show in Detail
                callReason = ""
                callSolution = ""

                uiScope.launch {
                        val newCall = EquipCall()
                        newCall.equipModule = equipModule
                        insert(newCall)         // new suspend fun insert
                        currentCall.value = currentCallFromDatabase()
                }

        }

        private suspend fun insert(newCall: EquipCall) {
                withContext(Dispatchers.IO) {
                        database.insert(newCall)        // DAO method insert
                }
        }

        fun detailTracking() {
                uiScope.launch {
                        val existingCall = currentCall.value ?: return@launch
                        existingCall.reasonCall = callReason
                        existingCall.remedyCall = callSolution
                        update(existingCall)
                        currentCall.value = currentCallFromDatabase()
                }
        }

        // cr 7: Other click handlers - follow the patterns of onStartTracking()
        fun onStopTracking() {
                uiScope.launch {
                        val oldCall = currentCall.value ?: return@launch
                        oldCall.endTimeMilli = System.currentTimeMillis()
                        update(oldCall)
                        currentCall.value = null
                }
        }

        private suspend fun update(oldCall: EquipCall) { Log.i("LogViewModel", "debug: 1 update: " + currentCall.value)
                withContext(Dispatchers.IO) {

                        oldCall.reasonCall = callReason
                        oldCall.remedyCall = callSolution

                        database.update(oldCall)
                }
        }

        fun onClear() {

                Toast.makeText(context, "All Records are deleted", Toast.LENGTH_LONG).show()
                uiScope.launch {
                        clear()
                        currentCall.value = null
                }
        }

        suspend fun clear() {
                withContext(Dispatchers.IO) {
                        database.clear()
                }
        }

        // called from fragment_log > buttonDetail
        fun btnDetailVisible() = Transformations.map(currentCall) {
                null != it
        }

        // called from fragment_log > buttonStop
        fun btnStopVisible() = Transformations.map(currentCall) {
                null != it
        }

}
