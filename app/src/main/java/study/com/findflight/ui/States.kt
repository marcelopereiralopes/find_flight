package study.com.findflight.ui

import study.com.findflight.domain.FlightsModel

open class State

object LoadingState : State()

data class ErrorState(val error: Throwable) : State()

data class SuccessState(val flights: FlightsModel) : State() {
    companion object {
        fun from(flights: FlightsModel): SuccessState {
            return SuccessState(flights)
        }
    }
}