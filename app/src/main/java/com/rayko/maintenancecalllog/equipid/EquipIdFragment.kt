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
import com.rayko.maintenancecalllog.databinding.FragmentEquipIdBinding
import com.rayko.maintenancecalllog.equipment.EquipmentViewModel
import com.rayko.maintenancecalllog.log.LogViewModel


class EquipIdFragment : Fragment() {

    // may not need for using factory?
//    private lateinit var viewModel: EquipIdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEquipIdBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_equip_id, container, false
        )

        // may not need for using factory?
//        viewModel = ViewModelProvider(this).get(EquipIdViewModel::class.java)

        val viewModelFactory = EquipIdViewModelFactory()

        val equipIdViewModel =
            ViewModelProvider(this, viewModelFactory).get(EquipIdViewModel::class.java)

        binding.buttonDIOSS15.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipIdFragment_to_logFragment)
        }

        return binding.root
    }

}