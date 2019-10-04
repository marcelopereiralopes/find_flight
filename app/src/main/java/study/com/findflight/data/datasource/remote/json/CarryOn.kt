package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CarryOn(
    @SerializedName("weight")
    @Expose
    var weight: Int? = null,
    @SerializedName("prices")
    @Expose
    var prices: List<Int>? = null
)
