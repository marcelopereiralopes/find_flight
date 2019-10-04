package study.com.findflight.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import study.com.findflight.ui.ErrorState
import study.com.findflight.ui.LoadingState
import study.com.findflight.ui.SuccessState


class InboundFlightsFragment : FlightsBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.flightState.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is LoadingState -> {
                        loadingConfig()
                    }
                    is SuccessState -> {
                        successConfig(state.flights.inboundFlightModel)
                    }
                    is ErrorState -> {
                        errorConfig()
                    }
                }
            }
        })

        model.flightFilteredState.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is LoadingState -> {
                        loadingConfig()
                    }
                    is SuccessState -> {
                        successConfig(state.flights.inboundFlightModel)
                    }

                }
            }
        })
    }
}