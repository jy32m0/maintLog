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
import com.rayko.maintenancecalllog.callReason
import com.rayko.maintenancecalllog.callSolution
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentDetailBinding
import com.rayko.maintenancecalllog.databinding.FragmentMiscEquipIdBinding


class DetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        //****************** Connect to LogViewModel *********************//
        // throw illegalArgumentException if the value is null
        val application = requireNotNull(this.activity).application

        // reference to dataSource via reference to DAO
        val dataSource = EquipCallDatabase.getInstance(application).equipCallDatabaseDao

        // create an instance to LogViewModelFactory
        val viewModelFactory = LogViewModelFactory(dataSource, application)

        // request ViewModelProvider for an instance of a LogViewModel
        val logViewModel : LogViewModel =
            ViewModelProvider(this, viewModelFactory).get(LogViewModel::class.java)
        //****************** *********************** *********************//

        if (callReason != "") {
            binding.logReason.setText(callReason)
        }
        if (callSolution != "") {
            binding.logSolution.setText(callSolution)
        }

        binding.btnDone.setOnClickListener { view: View ->
            callReason = binding.logReason.text.toString()
            callSolution = binding.logSolution.text.toString()
            logViewModel.detailTracking()
            view.findNavController().navigateUp()
        }

        binding.btnCancel.setOnClickListener { view: View ->
            view.findNavController().navigateUp()
        }

        return binding.root
    }

}