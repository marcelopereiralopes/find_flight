package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Checkout(
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("target")
    @Expose
    var target: String? = null
)
