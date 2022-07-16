package com.rayko.maintenancecalllog

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.rayko.maintenancecalllog.databinding.FragmentEquipmentBinding


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
        viewModel = ViewModelProviders.of(this).get(EquipmentViewModel::class.java)

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
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    //    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(EquipmentViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}