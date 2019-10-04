package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Trip(
    @SerializedName("layover")
    @Expose
    var layover: Int? = null,
    @SerializedName("stops")
    @Expose
    var stops: Int? = null,
    @SerializedName("aircraft")
    @Expose
    var aircraft: String? = null,
    @SerializedName("duration")
    @Expose
    var duration: Int? = null,
    @SerializedName("carrier")
    @Expose
    var carrier: String? = null,
    @SerializedName("flightNumber")
    @Expose
    var flightNumber: String? = null,
    @SerializedName("from")
    @Expose
    var from: String? = null,
    @SerializedName("isInternational")
    @Expose
    var isInternational: Boolean? = null,
    @SerializedName("departureDate")
    @Expose
    var departureDate: String? = null,
    @SerializedName("arrivalDate")
    @Expose
    var arrivalDate: String? = null,
    @SerializedName("to")
    @Expose
    var to: String? = null
)