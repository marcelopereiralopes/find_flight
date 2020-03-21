package study.com.findflight.ui.filter


sealed class Filter {
    class PeriodDayFilter(var parts: MutableList<String>) : Filter()
    class NumberStopsFilter(var number: MutableList<String>) : Filter()
}
