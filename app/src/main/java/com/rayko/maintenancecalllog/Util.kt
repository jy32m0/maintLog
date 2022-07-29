package com.rayko.maintenancecalllog

import android.content.res.Resources
import android.net.wifi.aware.AwareResources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.location.LocationRequestCompat
import androidx.core.text.HtmlCompat
import com.rayko.maintenancecalllog.database.EquipCall
import java.lang.StringBuilder
import java.text.SimpleDateFormat

fun formatCalls(calls: List<EquipCall>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.logTitle))
        calls.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                append("<b>End:</b>")
                append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
                append("<b>Hours:Minutes:Seconds</b>")
                // Hours
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 /60 / 60}")
                // Minutes
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 /60}")
                // Seconds
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}


