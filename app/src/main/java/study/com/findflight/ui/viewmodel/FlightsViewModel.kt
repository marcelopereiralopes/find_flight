package study.com.findflight.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import study.com.findflight.data.repository.FlightsRepository
import study.com.findflight.domain.FlightModel
import study.com.findflight.domain.FlightsModel
import study.com.findflight.ui.*
import study.com.findflight.SchedulerProvider
import study.com.findflight.util.SortFlightEnum


class FlightsViewModel(private val repository: FlightsRepository,
                       schedulerProvider: SchedulerProvider
) : CoroutineViewModel(schedulerProvider) {

    private val mState = MutableLiveData<State>()
    val state: LiveData<State>
        get() = mState

    fun getFights() = launch {
        mState.value = LoadingState
        try {
            val flights = repository.getFlights()
            mState.value = SuccessState.from(flights)
        } catch (e: Throwable) {
            mState.value = ErrorState(e)
        }
    }

    private val query = MutableLiveData<Pair<List<Filter>, Sort>>()

    val filterState: LiveData<State> = Transformations.switchMap(
        query,
        ::temp
    )

    private fun temp(pair: Pair<List<Filter>, Sort>) = searchFlightWithQuery(pair)

    fun filterSortFlightByParameters(pair: Pair<List<Filter>, Sort>) = apply {
        query.value = pair
    }

    private fun searchFlightWithQuery(pair: Pair<List<Filter>, Sort>): LiveData<State> {
        val listFilter = pair.first
        val sortByPrice = pair.second

        val periodDay: PeriodDayFilter = listFilter[0] as PeriodDayFilter
        val numberStops: NumberStopsFilter = listFilter[1] as NumberStopsFilter

        val flightsFiltered = MutableLiveData<State>()

        val flights = (mState.value as SuccessState).flights

        val inboundFlightModelFiltered = mutableListOf<FlightModel>()
        flights.inboundFlightModel.forEach {
            if ((periodDay.parts.contains(it.period) || periodDay.parts[0] == "")
                && (numberStops.number.contains(it.stops.toString()) || numberStops.number[0] == "")
            )
                inboundFlightModelFiltered.add(it)
        }

        val outboundFlightModelFiltered = mutableListOf<FlightModel>()
        flights.outboundFlightModel.forEach {
            if ((periodDay.parts.contains(it.period) || periodDay.parts[0] == "")
                && (numberStops.number.contains(it.stops.toString()) || numberStops.number[0] == "")
            )
                outboundFlightModelFiltered.add(it)
        }

        if ((sortByPrice as SortByPrice).value == SortFlightEnum.BIGGESTPRICE.name){
            inboundFlightModelFiltered.reverse()
            outboundFlightModelFiltered.reverse()
        }

        flightsFiltered.value =
            SuccessState(FlightsModel(inboundFlightModelFiltered, outboundFlightModelFiltered))

        return flightsFiltered
    }
}