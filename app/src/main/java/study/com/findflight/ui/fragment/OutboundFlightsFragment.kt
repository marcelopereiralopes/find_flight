package study.com.findflight.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_list_flights.*
import study.com.findflight.ui.ErrorState
import study.com.findflight.ui.SuccessState
import study.com.findflight.ui.LoadingState


class OutboundFlightsFragment : FlightsBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.flightState.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is LoadingState -> {
                        progressBar.visibility = View.VISIBLE
                        emptyList.visibility = View.GONE
                    }
                    is SuccessState -> {
                        progressBar.visibility = View.GONE
                        if (state.flights.outboundFlightModel.isEmpty())
                            emptyList.visibility = View.VISIBLE
                        else
                            emptyList.visibility = View.GONE
                        adapter.update(state.flights.outboundFlightModel)
                    }
                    is ErrorState -> {
                        progressBar.visibility = View.GONE
                        emptyList.visibility = View.VISIBLE
                    }
                }
            }
        })

        model.flightFilteredState.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is LoadingState -> {
                        progressBar.visibility = View.VISIBLE
                        emptyList.visibility = View.GONE
                    }
                    is SuccessState -> {
                        progressBar.visibility = View.GONE
                        if (state.flights.outboundFlightModel.isEmpty())
                            emptyList.visibility = View.VISIBLE
                        else
                            emptyList.visibility = View.GONE
                        adapter.update(state.flights.outboundFlightModel)
                    }
                }
            }
        })
    }
}