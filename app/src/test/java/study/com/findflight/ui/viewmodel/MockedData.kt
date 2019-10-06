package study.com.findflight.ui.viewmodel

import study.com.findflight.domain.FlightModel
import study.com.findflight.domain.FlightsModel
import study.com.findflight.util.PeriodDayEnum


object MockedData {

    val flightsMockList = FlightsModel(
        mutableListOf(
            FlightModel(
                "BSB",
                "CNF",
                null,
                null,
                PeriodDayEnum.EVENING.period,
                1,
                100.00
            ),
            FlightModel(
                "TWT",
                "SAS",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                310.00
            )
        ), mutableListOf(
            FlightModel(
                "CNF",
                "BSB",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                150.00
            ),
            FlightModel(
                "XZT",
                "WAS",
                null,
                null,
                PeriodDayEnum.EVENING.period,
                1,
                350.00
            )
        )
    )

    val flightsOrderByLowestPriceMockList = FlightsModel(
        mutableListOf(
            FlightModel(
                "BSB",
                "CNF",
                null,
                null,
                PeriodDayEnum.EVENING.period,
                1,
                100.00
            ),
            FlightModel(
                "TWT",
                "SAS",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                310.00
            )
        ), mutableListOf(
            FlightModel(
                "CNF",
                "BSB",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                150.00
            ),
            FlightModel(
                "XZT",
                "WAS",
                null,
                null,
                PeriodDayEnum.EVENING.period,
                1,
                350.00
            )
        )
    )

    val flightsOrderByHighestPriceMockList = FlightsModel(
        mutableListOf(
            FlightModel(
                "TWT",
                "SAS",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                310.00
            ),
            FlightModel(
                "BSB",
                "CNF",
                null,
                null,
                PeriodDayEnum.EVENING.period,
                1,
                100.00
            )
        ), mutableListOf(
            FlightModel(
                "XZT",
                "WAS",
                null,
                null,
                PeriodDayEnum.EVENING.period,
                1,
                350.00
            ),
            FlightModel(
                "CNF",
                "BSB",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                150.00
            )
        )
    )

    val flightsMockListWithNightPeriod = FlightsModel(
        mutableListOf(
            FlightModel(
                "TWT",
                "SAS",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                310.00
            )
        ), mutableListOf(
            FlightModel(
                "CNF",
                "BSB",
                null,
                null,
                PeriodDayEnum.NIGHT.period,
                1,
                150.00
            )
        )
    )
}