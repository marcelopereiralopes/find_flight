package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Fee(
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("value")
    @Expose
    var value: Double? = null,
    @SerializedName("group")
    @Expose
    var group: String? = null,
    @SerializedName("passengerType")
    @Expose
    var passengerType: String? = null,
    @SerializedName("id")
    @Expose
    var id: Int? = null
)