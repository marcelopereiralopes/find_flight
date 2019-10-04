package study.com.findflight.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object Converter {

    fun stringDateToCalendar(source: String?): Calendar? {
        source?.let {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val date = inputFormat.parse(source)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            return calendar
        }
        return null
    }

    fun calendarExtractPartOfDay(calendar: Calendar?): String? {
        calendar?.let {
            return when(calendar.get(Calendar.HOUR_OF_DAY)){
                in 6 .. 11 -> PartsDayEnum.MORNING.name
                in 12 .. 17 -> PartsDayEnum.EVENING.name
                in 18 .. 23 -> PartsDayEnum.NIGHT.name
                else -> PartsDayEnum.DAWN.name
            }
        }

        return null
    }

    fun calendarExtractHourAndMinute(calendar: Calendar?): String? {
        calendar?.let {
            return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        }
        return null
    }

    fun calendarDiffHoursAndMinutes(before: Calendar?, after: Calendar?) : String? {
        if (before!=null && after!=null){
            val diff = (after.time.time - before.time.time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)%60
            val hour = TimeUnit.MILLISECONDS.toHours(diff)
            return "${hour}h:${minutes}"
        }
        return null
    }
}