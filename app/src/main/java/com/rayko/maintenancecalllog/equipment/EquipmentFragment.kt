package com.rayko.maintenancecalllog.equipment

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.rayko.maintenancecalllog.AboutFragment
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentEquipmentBinding
import com.rayko.maintenancecalllog.equipName
import com.rayko.maintenancecalllog.log.LogViewModel
import com.rayko.maintenancecalllog.log.LogViewModelFactory

class EquipmentFragment : Fragment(), MenuProvider {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentEquipmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_equipment, container, false
        )

        binding.btnDBCS.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipmentFragment_to_equipIdFragment)
        }

        fun buttonMiscToDo(view: View, btnName: String) {
            view.findNavController().navigate(R.id.action_equipmentFragment_to_miscEquipIdFragment)
            equipName = btnName     // variable held at util.kt
        }

        binding.btnAFCS.setOnClickListener {
            buttonMiscToDo(it, "AFCS")
        }

        binding.btnAFSM.setOnClickListener {
            buttonMiscToDo(it, "AFSM")
        }

        binding.btnAPBS.setOnClickListener {
            buttonMiscToDo(it, "APBS")
        }

        binding.btnSSM.setOnClickListener {
            buttonMiscToDo(it, "SSM")
        }

        // TO DO something else **************************
        binding.btnOthers.setOnClickListener {
            buttonMiscToDo(it, "AFCS")
        }

//vvvvv Substitute for setHasOptionsMenu which was deprecated. vvvvv
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//


        return binding.root
    }

//***** substitute for onCreateOptionsMenu which was deprecated
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.overflow_menu, menu)
    }

//vvvvv Substitute for onOptionsItemSelected which was deprecated. vvvvv
    override fun onMenuItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuViewRecords -> viewRecords()
            R.id.menuClearAllRecords -> clearAllRecords()
            R.id.menuAbout -> openAbout()
            else -> Toast.makeText(activity,
                "Selected item is not available. Try again.", Toast.LENGTH_SHORT).show()
        }
        return true
    }
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//

    private fun viewRecords() {
        findNavController().navigate(R.id.action_equipmentFragment_to_logFragment)
    }

    private fun clearAllRecords() {
        findNavController().navigate(R.id.action_equipmentFragment_to_logFragment)

        val result = "clear"
        setFragmentResult("requestKey", bundleOf("bundleKey" to result))
    }

    private fun openAbout() {
        findNavController().navigate(R.id.action_equipmentFragment_to_aboutFragment)
    }

}