package study.com.findflight.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import study.com.findflight.data.datasource.remote.FlightsDatasource
import study.com.findflight.data.datasource.remote.json.getFlightsModel
import study.com.findflight.domain.FlightsModel


class FlightsRepository(private val flightsDatasource: FlightsDatasource) {

    suspend fun getFlights(): FlightsModel {
        val flights = withContext(Dispatchers.Default) {
            flightsDatasource.getFlights()
        }.await()

        return flights.getFlightsModel()
    }
}