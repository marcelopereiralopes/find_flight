package study.com.findflight.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import study.com.findflight.data.repository.FlightsRepository
import study.com.findflight.domain.FlightModel
import study.com.findflight.domain.FlightsModel
import study.com.findflight.ui.*
import study.com.findflight.SchedulerProvider
import study.com.findflight.ui.filter.*
import study.com.findflight.util.SortFlightEnum


class FlightsViewModel(private val repository: FlightsRepository,
                       schedulerProvider: SchedulerProvider
) : CoroutineViewModel(schedulerProvider) {

    private val mState = MutableLiveData<State>()
    val state: LiveData<State>
        get() = mState

    fun getFlights() = launch {
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

    fun filterSortFlight(pair: Pair<List<Filter>, Sort>) = apply {
        query.value = pair
    }

    private fun searchFlightWithQuery(pair: Pair<List<Filter>, Sort>): LiveData<State> {
        val listFilter = pair.first
        val sortByPrice = pair.second

        val periodDay: Filter.PeriodDayFilter = listFilter[0] as Filter.PeriodDayFilter
        val numberStops: Filter.NumberStopsFilter = listFilter[1] as Filter.NumberStopsFilter

        val flightsFiltered = MutableLiveData<State>()

        val flights = (mState.value as SuccessState).flights

        val inboundFlightModelFiltered = mutableListOf<FlightModel>()
        flights.inboundFlightModel.forEach {
            if ((periodDay.parts.contains(it.period) || periodDay.parts.isEmpty())
                && (numberStops.number.contains(it.stops.toString()) || numberStops.number.isEmpty())
            )
                inboundFlightModelFiltered.add(it)
        }

        val outboundFlightModelFiltered = mutableListOf<FlightModel>()
        flights.outboundFlightModel.forEach {
            if ((periodDay.parts.contains(it.period) || periodDay.parts.isEmpty())
                && (numberStops.number.contains(it.stops.toString()) || numberStops.number.isEmpty())
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