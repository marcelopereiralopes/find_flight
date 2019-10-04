package study.com.findflight.ui


open class Query

data class PartsDayQuery(val parts: List<String>) : Query()

data class NumberStopsQuery(val number: List<Int>) : Query()