package com.rayko.maintenancecalllog.equipment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.rayko.maintenancecalllog.AboutFragment
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.databinding.FragmentEquipmentBinding
import com.rayko.maintenancecalllog.log.LogViewModel


class EquipmentFragment : Fragment() {

    private lateinit var viewModel: EquipmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEquipmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_equipment, container, false
        )

        // Reference to the viewModel (never construct)
        Log.i("EquipmentFragment", "viewModelProvider is referenced.")
        viewModel = ViewModelProvider(this).get(EquipmentViewModel::class.java)

        binding.btnDBCS.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipmentFragment_to_equipIdFragment)
        }

        binding.btnAFCS.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipmentFragment_to_miscEquipIdFragment)
        }

        binding.btnAFSM.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipmentFragment_to_miscEquipIdFragment)
        }

        binding.btnAPBS.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipmentFragment_to_miscEquipIdFragment)
        }

        binding.btnSSM.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipmentFragment_to_miscEquipIdFragment)
        }

//        binding.btnOthers.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_equipmentFragment_to_miscEquipIdFragment)
//        }

        // for menu
        setHasOptionsMenu(true)

        return binding.root
    }

    // along with setHasOptionsMenu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuViewRecords -> viewRecords()
            R.id.menuClearAllRecords -> clearAllRecords()
            R.id.menuAbout -> openAbout()
            else -> Toast.makeText(activity,"Selected item is not available. Try again.", Toast.LENGTH_SHORT)
        }
        return true
    }


    private fun viewRecords() {
        findNavController().navigate(R.id.action_equipmentFragment_to_logFragment)
    }

    private fun clearAllRecords() {
        findNavController().navigate(R.id.action_equipmentFragment_to_logFragment)
    }

    private fun openAbout() {
        findNavController().navigate(R.id.action_equipmentFragment_to_aboutFragment)
    }
    //    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(EquipmentViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}