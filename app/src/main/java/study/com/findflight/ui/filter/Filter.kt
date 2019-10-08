package study.com.findflight.ui.filter


open class Filter

data class PeriodDayFilter(var parts: MutableList<String>) : Filter()

data class NumberStopsFilter(var number: MutableList<String>) : Filter()