package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Adult(
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null,
    @SerializedName("fare")
    @Expose
    var fare: Double? = null,
    @SerializedName("fees")
    @Expose
    var fees: List<Fee>? = null
)
