package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Outbound(
    @SerializedName("stops")
    @Expose
    var stops: Int? = null,
    @SerializedName("airline")
    @Expose
    var airline: String? = null,
    @SerializedName("otaAvailableIn")
    @Expose
    var otaAvailableIn: String? = null,
    @SerializedName("duration")
    @Expose
    var duration: Int? = null,
    @SerializedName("flightNumber")
    @Expose
    var flightNumber: String? = null,
    @SerializedName("airlineTarget")
    @Expose
    var airlineTarget: String? = null,
    @SerializedName("from")
    @Expose
    var from: String? = null,
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("choosedTripType")
    @Expose
    var choosedTripType: String? = null,
    @SerializedName("availableIn")
    @Expose
    var availableIn: String? = null,
    @SerializedName("trips")
    @Expose
    var trips: List<Trip>? = null,
    @SerializedName("departureDate")
    @Expose
    var departureDate: String? = null,
    @SerializedName("arrivalDate")
    @Expose
    var arrivalDate: String? = null,
    @SerializedName("cabin")
    @Expose
    var cabin: String? = null,
    @SerializedName("pricing")
    @Expose
    var pricing: Pricing? = null,
    @SerializedName("direction")
    @Expose
    var direction: String? = null,
    @SerializedName("to")
    @Expose
    var to: String? = null
)