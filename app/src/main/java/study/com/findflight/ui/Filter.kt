package study.com.findflight.ui


open class Filter

data class PeriodDayFilter(val parts: List<String>) : Filter()

data class NumberStopsFilter(val number: List<String>) : Filter()