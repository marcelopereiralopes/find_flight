package study.com.findflight.ui.viewmodel

import study.com.findflight.domain.FlightModel
import study.com.findflight.domain.FlightsModel
import study.com.findflight.util.PeriodDayEnum
import java.util.*


object MockedData {

    val mockList = FlightsModel(
        mutableListOf(
            FlightModel(
                "BSB",
                "CNF",
                Calendar.getInstance(),
                Calendar.getInstance(),
                PeriodDayEnum.EVENING.period,
                1,
                100.00
            ),
            FlightModel(
                "TWT",
                "SAS",
                Calendar.getInstance(),
                Calendar.getInstance(),
                PeriodDayEnum.EVENING.period,
                0,
                310.00
            )
        ), mutableListOf(
            FlightModel(
                "CNF",
                "BSB",
                Calendar.getInstance(),
                Calendar.getInstance(),
                PeriodDayEnum.NIGHT.period,
                1,
                150.00
            ),
            FlightModel(
                "XZT",
                "WAS",
                Calendar.getInstance(),
                Calendar.getInstance(),
                PeriodDayEnum.NIGHT.period,
                0,
                350.00
            )
        )
    )
}