package study.com.findflight.util


enum class PeriodDayEnum(val period: String) {
    MORNING("1"),
    EVENING("2"),
    NIGHT("3"),
    DAWN("4")
}

enum class NumberStopEnum(val value: String) {
    ZERO("0"),
    ONE("1")
}

enum class SortFlightEnum {
    BIGGESTPRICE,
    LOWESTPRICE,
    LOWESTPRICEANDTIME
}