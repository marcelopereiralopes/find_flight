package study.com.findflight.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import study.com.findflight.data.repository.FlightsRepository
import study.com.findflight.domain.FlightModel
import study.com.findflight.domain.FlightsModel
import study.com.findflight.ui.*


class FlightsViewModel(private val repository: FlightsRepository) : CoroutineViewModel() {

    private val mFlightState = MutableLiveData<State>()
    val flightState: LiveData<State>
        get() = mFlightState

    fun getFights() = launch {
        mFlightState.value = LoadingState
        try {
            val flights = repository.getFlights()
            mFlightState.value = SuccessState.from(flights)
        } catch (e: Throwable) {
            mFlightState.value = ErrorState(e)
        }
    }

    private val query = MutableLiveData<Pair<PeriodDayQuery, NumberStopsQuery>>()

    val flightFilteredState: LiveData<State> = Transformations.switchMap(
        query,
        ::temp
    )

    private fun temp(pair: Pair<PeriodDayQuery, NumberStopsQuery>) = searchFlightWithQuery(pair)

    fun searchFlightByQuery(pair: Pair<PeriodDayQuery, NumberStopsQuery>) = apply {
        query.value = pair
    }

    private fun searchFlightWithQuery(pair: Pair<PeriodDayQuery, NumberStopsQuery>): LiveData<State> {
        val partsDay = pair.first.parts
        val numberStops = pair.second.number

        val flightsFiltered = MutableLiveData<State>()

        val flights = (mFlightState.value as SuccessState).flights

        val inboundFlightModelFiltered = mutableListOf<FlightModel>()
        flights.inboundFlightModel.forEach {
            if ((partsDay.contains(it.period) || partsDay[0]=="")
                && (numberStops.contains(it.stops.toString()) || numberStops[0]==""))
                inboundFlightModelFiltered.add(it)
        }

        val outboundFlightModelFiltered = mutableListOf<FlightModel>()
        flights.outboundFlightModel.forEach {
            if ((partsDay.contains(it.period) || partsDay[0]=="")
                && (numberStops.contains(it.stops.toString()) || numberStops[0]==""))
                outboundFlightModelFiltered.add(it)
        }

        flights.inboundFlightModel

        flightsFiltered.value =
            SuccessState(FlightsModel(inboundFlightModelFiltered, outboundFlightModelFiltered))

        return flightsFiltered
    }
}