package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Checked(
    @SerializedName("weight")
    @Expose
    var weight: Any? = null,
    @SerializedName("prices")
    @Expose
    var prices: List<Any>? = null
)
