package study.com.findflight.util

import java.text.NumberFormat
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
            return when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 6..11 -> PeriodDayEnum.MORNING.period
                in 12..17 -> PeriodDayEnum.EVENING.period
                in 18..23 -> PeriodDayEnum.NIGHT.period
                else -> PeriodDayEnum.DAWN.period
            }
        }

        return null
    }

    fun calendarExtractHourAndMinute(calendar: Calendar?): String? {
        calendar?.let {
            val hour = paddingLeftIfNecessary(calendar.get(Calendar.HOUR_OF_DAY))
            val minute = paddingLeftIfNecessary(calendar.get(Calendar.MINUTE))
            return "$hour:$minute"
        }
        return null
    }

    fun calendarDiffHoursAndMinutes(before: Calendar?, after: Calendar?): String? {
        if (before != null && after != null) {
            val diff = (after.time.time - before.time.time)
            val minutes = paddingLeftIfNecessary((TimeUnit.MILLISECONDS.toMinutes(diff) % 60).toInt())
            val hour = paddingLeftIfNecessary(TimeUnit.MILLISECONDS.toHours(diff).toInt())
            return "${hour}h:$minutes"
        }
        return null
    }

    fun currencyPtBrFormatter(value: Double?): String? {
        value?.let {
            val ptBr = Locale("pt", "BR")
            return NumberFormat.getCurrencyInstance(ptBr).format(value.toLong())
        }
        return null
    }

    private fun paddingLeftIfNecessary(value: Int) =
        if (value < 10) {
            "0$value"
        } else {
            "$value"
        }

}