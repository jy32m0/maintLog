package com.rayko.maintenancecalllog

import android.app.AlertDialog
import android.content.Context

class MessageDialog (context: Context) : AlertDialog.Builder(context){

    lateinit var onResponse:(r: ResponseType) -> Unit

    enum class ResponseType {
        YES, CANCEL     //CONFIRM, CANCEL
    }

    fun showDialog(title: String, message: String, listener: (r: ResponseType) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        onResponse = listener

        builder.setPositiveButton("Yes") { _, _ ->
            onResponse(ResponseType.YES)
        }

        builder.setPositiveButton("Cancel") { _, _ ->
            onResponse(ResponseType.CANCEL)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}

