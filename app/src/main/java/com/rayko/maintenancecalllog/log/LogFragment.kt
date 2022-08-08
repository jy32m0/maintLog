package com.rayko.maintenancecalllog.log

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentLogBinding


class LogFragment : Fragment() {

    private lateinit var logViewModel: LogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("LogFragment", "Line 20: onCreated")


        setFragmentResultListener("requestKey") {
            requestKey, bundle ->
            val result = bundle.getString("bundleKey")
            if (result == "clear") {
                logViewModel.onClear()
            }

            Log.i("No ViewModel", "Line 30: resultListener" + result)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLogBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_log, container, false
        )

        //****************** Connect to LogViewModel *********************//
        // throw illegalArgumentException if the value is null
        val application = requireNotNull(this.activity).application

        // reference to dataSource via reference to DAO
        val dataSource = EquipCallDatabase.getInstance(application).equipCallDatabaseDao

        // create an instance to LogViewModelFactory
        val viewModelFactory = LogViewModelFactory(dataSource, application)

        // request ViewModelProvider for an instance of a LogViewModel
        logViewModel =
            ViewModelProvider(this, viewModelFactory).get(LogViewModel::class.java)
        //****************** *********************** *********************//

        binding.logViewModel = logViewModel

        // current activity as lifecycleowner of the binding to absorb live data updates
        // instead of fragment as the LifecycleOwner, using its view LifeCycle
        // Without this, logResult won't show in logText
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnDetail.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_logFragment_to_detailFragment)
        }

        binding.btnStop.setOnClickListener { view: View ->
            logViewModel.onStopTracking()
        }

        binding.btnLogExit.setOnClickListener { view: View ->
            view.findNavController().navigateUp()}

        return  binding.root

    }

}