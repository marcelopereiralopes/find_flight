package study.com.findflight.data.datasource.remote.json

import com.google.gson.annotations.Expose


data class Flights(
    @Expose var inbound: MutableList<Inbound>? = null,
    @Expose var outbound: MutableList<Outbound>? = null
)