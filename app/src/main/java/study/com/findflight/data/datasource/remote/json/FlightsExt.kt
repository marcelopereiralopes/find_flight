package study.com.findflight.data.datasource.remote.json

import study.com.findflight.domain.FlightModel
import study.com.findflight.domain.FlightsModel
import study.com.findflight.util.Converter
import java.util.*


fun Flights.getFlightsModel(): FlightsModel {
    val inboundFlightModelList: MutableList<FlightModel> = mutableListOf()
    inbound.orEmpty()
        .sortedBy { it.pricing?.ota?.saleTotal }
        .map {
            val departure = Converter.stringDateToCalendar(it.trips?.first()?.departureDate)
            val arrival = Converter.stringDateToCalendar(it.trips?.last()?.arrivalDate)
            val from = it.trips?.first()?.from
            val to = it.trips?.last()?.to
            val stops = it.stops
            val pricing = it.pricing?.ota?.saleTotal

            safeInsertList(departure, arrival, from, to, stops, pricing, inboundFlightModelList)

        }

    val outboundFlightModelList: MutableList<FlightModel> = mutableListOf()
    outbound.orEmpty()
        .sortedBy { it.pricing?.ota?.saleTotal }
        .map {
            val departure = Converter.stringDateToCalendar(it.trips?.first()?.departureDate)
            val arrival = Converter.stringDateToCalendar(it.trips?.last()?.arrivalDate)
            val from = it.trips?.first()?.from
            val to = it.trips?.last()?.to
            val stops = it.stops
            val pricing = it.pricing?.ota?.saleTotal

            safeInsertList(departure, arrival, from, to, stops, pricing, outboundFlightModelList)

        }

    return FlightsModel(inboundFlightModelList, outboundFlightModelList)
}

private fun safeInsertList(
    departure: Calendar?, arrival: Calendar?, from: String?, to: String?,
    stops: Int?, pricing: Double?, flightModelList: MutableList<FlightModel>
) {
    if (departure != null && arrival != null && from != null && to != null && stops != null
        && pricing != null
    ) {
        flightModelList.add(
            FlightModel(
                from, to, departure, arrival,
                Converter.calendarExtractPartOfDay(departure), stops, pricing
            )
        )
    }
}