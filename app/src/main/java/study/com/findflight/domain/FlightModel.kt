package study.com.findflight.domain

import java.util.*


data class FlightModel(var from: String?,
                       var to: String?,
                       var departure: Calendar?,
                       var arrival: Calendar?,
                       var period: String?,
                       var stops: Int?,
                       var value: Double?)