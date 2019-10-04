package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Luggage(
    @SerializedName("carryOn")
    @Expose
    var carryOn: CarryOn? = null,
    @SerializedName("checked")
    @Expose
    var checked: Checked? = null
)