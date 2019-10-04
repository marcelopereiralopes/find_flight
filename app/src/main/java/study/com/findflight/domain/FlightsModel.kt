package study.com.findflight.domain


data class FlightsModel(val inboundFlightModel: MutableList<FlightModel>,
                        val outboundFlightModel: MutableList<FlightModel>)