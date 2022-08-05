package com.rayko.maintenancecalllog

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentMiscEquipIdBinding
import com.rayko.maintenancecalllog.log.LogViewModel
import com.rayko.maintenancecalllog.log.LogViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [MiscEquipIdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MiscEquipIdFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMiscEquipIdBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_misc_equip_id, container, false
        )

        //vvvvv Use logViewModel... vvvvv

        // throw illegalArgumentException if the value is null
        val application = requireNotNull(this.activity).application
        // reference to dataSource via reference to DAO
        val dataSource = EquipCallDatabase.getInstance(application).equipCallDatabaseDao
        // create an instance to LogViewModelFactory
        val viewModelFactory = LogViewModelFactory(dataSource, application)
        // request ViewModelProvider for an instance of a LogViewModel
        val logViewModel =
            ViewModelProvider(this, viewModelFactory).get(LogViewModel::class.java)

        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        fun buttonToDo(view: View, machID: String) {
            view.findNavController().navigate(R.id.action_miscEquipIdFragment_to_logFragment)
            logViewModel.onStartTracking()
        }

        binding.buttonMisc1.setOnClickListener {
            Log.i("MiscEquip", "debug: 65 buttonMisc1")
            buttonToDo(it, "1")
        }

        binding.buttonMisc2.setOnClickListener {
            buttonToDo(it, "2")
        }

        binding.buttonMisc3.setOnClickListener {
            buttonToDo(it, "3")
        }

        binding.buttonMisc4.setOnClickListener {
            buttonToDo(it, "4")
        }

        binding.buttonMiscCancel.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_miscEquipIdFragment_to_equipmentFragment)
            logViewModel.pubOnCleared()
        }

        return binding.root
    }

}