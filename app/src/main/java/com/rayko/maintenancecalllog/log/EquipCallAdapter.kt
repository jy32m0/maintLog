package com.rayko.maintenancecalllog.log

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rayko.maintenancecalllog.LogItemViewHolder
import com.rayko.maintenancecalllog.R
import com.rayko.maintenancecalllog.database.EquipCall

class EquipCallAdapter: RecyclerView.Adapter<LogItemViewHolder> () {
    var data = listOf<EquipCall>()
    set(value) {
        field = value
        notifyDataSetChanged()  // temporary
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LogItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.callId.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.log_detail_view, parent, false) as TextView
        return LogItemViewHolder(view)
    }
}