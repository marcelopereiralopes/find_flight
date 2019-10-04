package study.com.findflight.ui


open class Query

data class PeriodDayQuery(val parts: List<String>) : Query()

data class NumberStopsQuery(val number: List<String>) : Query()