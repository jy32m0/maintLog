package com.rayko.maintenancecalllog.equipid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentEquipIdBinding
import com.rayko.maintenancecalllog.equipment.EquipmentViewModel
import com.rayko.maintenancecalllog.log.LogViewModel
import com.rayko.maintenancecalllog.log.LogViewModelFactory


class EquipIdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEquipIdBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_equip_id, container, false
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
            view.findNavController().navigate(R.id.action_equipIdFragment_to_logFragment)
            logViewModel.onStartTracking(machID)
        }

        binding.buttonDIOSS15.setOnClickListener {
            buttonToDo(it, "DIOSS 15")
        }

        binding.buttonDIOSS16.setOnClickListener {
            buttonToDo(it, "DIOSS 16")
        }

        binding.buttonDBCS1.setOnClickListener {
            buttonToDo(it, "DBCS 01")
        }

        binding.buttonDBCS2.setOnClickListener {
            buttonToDo(it, "DBCS 02")
        }

        binding.buttonDBCS5.setOnClickListener {
            buttonToDo(it, "DBCS 05")
        }

        binding.buttonDBCS9.setOnClickListener {
            buttonToDo(it, "DBCS 09")
        }

        binding.buttonDBCS10.setOnClickListener {
            buttonToDo(it, "DBCS 10")
        }

        binding.buttonDBCS12.setOnClickListener {
            buttonToDo(it, "DBCS 12")
        }

        binding.buttonDBCS13.setOnClickListener {
            buttonToDo(it, "DBCS 13")
        }

        binding.buttonDBCS17.setOnClickListener {
            buttonToDo(it, "DBCS 17")
        }

        binding.buttonDBCS18.setOnClickListener {
            buttonToDo(it, "DBCS 18")
        }

        binding.buttonDBCS19.setOnClickListener {
            buttonToDo(it, "DBCS 19")
        }

        binding.buttonDBCS20.setOnClickListener {
            buttonToDo(it, "DBCS 20")
        }

        binding.buttonCIOSS2.setOnClickListener {
            buttonToDo(it, "CIOSS 2")
        }

        binding.buttonMPECancel.setOnClickListener { view: View ->
            view.findNavController().navigateUp()
        }

        return binding.root
    }

}