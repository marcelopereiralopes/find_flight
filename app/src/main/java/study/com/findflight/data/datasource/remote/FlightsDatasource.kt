package study.com.findflight.data.datasource.remote

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import study.com.findflight.data.datasource.remote.json.Flights


interface FlightsDatasource {

    @GET("hmg-search")
    fun getFlights(): Deferred<Flights>
}