package com.rayko.maintenancecalllog.log

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentLogBinding


class LogFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("LogFragment", "Line 20: onCreated")
        // start_time_milli
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLogBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_log, container, false
        )
        Log.i("LogFragment", "Line 31: onCreateView kicked in.")

        // throw illegalArgumentException if the value is null
        val application = requireNotNull(this.activity).application

        // reference to dataSource via reference to DAO
        val dataSource = EquipCallDatabase.getInstance(application).equipCallDatabaseDao

        // create an instance to LogViewModelFactory
        val viewModelFactory = LogViewModelFactory(dataSource, application)

        // request ViewModelProvider for an instance of a LogViewModel
        val logViewModel =
            ViewModelProvider(this, viewModelFactory).get(LogViewModel::class.java)

        binding.logViewModel = logViewModel

        // current activity as lifecycleowener of the binding to absorb live data updates
        // instead of fragment as the LifecycleOwner, using its view LifeCycle
        binding.lifecycleOwner = viewLifecycleOwner

        logViewModel.onStartTracking()

        binding.btnStop.setOnClickListener { view: View ->
            logViewModel.onStopTracking()
//            view.findNavController().navigate(R.id.action_logFragment_to_equipmentFragment)
        }

        return  binding.root

        // Inflate the layout for this fragment ** but buttons won't click
//        return inflater.inflate(R.layout.fragment_log, container, false)
    }

}