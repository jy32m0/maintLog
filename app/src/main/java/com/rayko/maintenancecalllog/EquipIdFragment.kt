package com.rayko.maintenancecalllog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.rayko.maintenancecalllog.databinding.FragmentEquipIdBinding
import com.rayko.maintenancecalllog.databinding.FragmentEquipmentBinding


class EquipIdFragment : Fragment() {

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
        binding.buttonDIOSS15.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_equipIdFragment_to_logFragment)
        }

        return binding.root
    }

}