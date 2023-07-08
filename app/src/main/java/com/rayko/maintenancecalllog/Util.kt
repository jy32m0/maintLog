
package com.rayko.maintenancecalllog

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.rayko.maintenancecalllog.database.EquipCall
import java.lang.Math.floor
import java.lang.StringBuilder
import java.text.SimpleDateFormat

var equipName: String = "Miscellaneous"     // except for DBCS platforms
var callReason: String = ""
var callSolution: String = ""

fun formatCalls(calls: List<EquipCall>, resources: Resources): Spanned {
    val sb = StringBuilder()
    var hours: Int
    var mins: Int
    var secs: Int

    sb.apply {
        append(resources.getString(R.string.logTitle))
        calls.forEach {
//            append("<br>")

            if (it.endTimeMilli != it.startTimeMilli) {
                // Double to use floor function.
                val totalTimeSec : Double = (it.endTimeMilli.minus(it.startTimeMilli)) / 1000.0
                hours = floor(totalTimeSec / 3600).toInt()
                mins = floor((totalTimeSec % 3660) / 60).toInt()
                secs = ((totalTimeSec % 3600) % 60).toInt()

                append("<p style='color: blue'>")
                append("<b>${it.equipModule}</b><br>")
                append(resources.getString(R.string.start_time))
                append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
                append("<b>End:</b>")
                append("\t ${convertLongToDateString(it.endTimeMilli)}<br>")
                append("<style='color: blue'>")
                append("<b>Maint Time: </b>")
                append("\t $hours")
                append(" Hour and")
                append("\t $mins")
                append(" Minutes<br>")
                append("<b>Problem: </b>${it.reasonCall}<br>")
                append("<b>Solution: </b>${it.remedyCall}<br><br>")
                append("</p>")
//                append("\t $secs")
//                append(" Seconds<br><br>")
            } else {
                append("<b>${it.equipModule}</b><br>")
                append(resources.getString(R.string.start_time))
                append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
                append("<b>Problem: </b>${it.reasonCall}<br>")
                append("<b>Solution: </b>${it.remedyCall}<br><br>")
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

class LogItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)