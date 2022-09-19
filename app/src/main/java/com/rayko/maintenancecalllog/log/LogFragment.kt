package com.rayko.maintenancecalllog.log

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rayko.maintenancecalllog.MiscEquipIdFragmentArgs
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.database.EquipCall
import com.rayko.maintenancecalllog.database.EquipCallDatabase
import com.rayko.maintenancecalllog.databinding.FragmentLogBinding
import com.rayko.maintenancecalllog.formatCalls


class LogFragment : Fragment(), MenuProvider {

    private lateinit var logViewModel: LogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("LogFragment", "Line 20: onCreated")

        // called from EquipmentFragment.kt > clearAllRecords()
        setFragmentResultListener("clearKey") {
                clearKey, bundle ->
            val result = bundle.getString("bundleKey")
            if (result == "clear") {
                logViewModel.onClear()
            }

            Log.i("No ViewModel", "debug: resultListener" + result)
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

        binding.logViewModel = logViewModel
        //****************** *********************** *********************//

        var args = LogFragmentArgs.fromBundle(requireArguments())

        if (args != null) {
            logViewModel.onStartTracking(args.toString())
        }

        // current activity as lifecycleOwner of the binding to absorb live data updates
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



        // step 34 - Substitute for setHasOptionsMenu which was deprecated
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return  binding.root
    }

    //***** substitute for onCreateOptionsMenu which was deprecated
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.share_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.share -> shareSuccess()
                16908332 -> view?.findNavController()?.navigateUp()     // navigateUp button
                else -> (Toast.makeText(activity,
                    "Wow! Selected item is not available. Try again. " + menuItem.itemId, Toast.LENGTH_SHORT).show())
            }
            return true
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    } 

    private fun getShareIntent(): Intent {

        return ShareCompat.IntentBuilder(activity!!)
            .setText(logViewModel.activeCall.toString())
            .setType("text/plain")
            .intent
    }

}