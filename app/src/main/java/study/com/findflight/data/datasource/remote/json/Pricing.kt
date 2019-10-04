package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pricing(
    @SerializedName("ota")
    @Expose
    var ota: Ota? = null,
    @SerializedName("airlineName")
    @Expose
    var airlineName: String? = null,
    @SerializedName("isInternational")
    @Expose
    var isInternational: Boolean? = null,
    @SerializedName("bestPriceAt")
    @Expose
    var bestPriceAt: String? = null,
    @SerializedName("mmBestPriceAt")
    @Expose
    var mmBestPriceAt: String? = null,
    @SerializedName("savingPercentage")
    @Expose
    var savingPercentage: String? = null
)
