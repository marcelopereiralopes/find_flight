package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ota(
    @SerializedName("adult")
    @Expose
    var adult: Adult? = null,
    @SerializedName("luggage")
    @Expose
    var luggage: Luggage? = null,
    @SerializedName("checkout")
    @Expose
    var checkout: Checkout? = null,
    @SerializedName("familyCode")
    @Expose
    var familyCode: String? = null,
    @SerializedName("fareTotal")
    @Expose
    var fareTotal: Double? = null,
    @SerializedName("saleTotal")
    @Expose
    var saleTotal: Double? = null
)
