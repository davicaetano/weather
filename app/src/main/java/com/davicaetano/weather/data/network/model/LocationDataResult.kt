package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.Location
import com.google.gson.annotations.SerializedName

data class LocationDataResult(
    @SerializedName("name"         ) var name        : String,
    @SerializedName("lat"          ) var lat         : Double,
    @SerializedName("lon"          ) var lon         : Double,
    @SerializedName("country"      ) var country     : String?,
    @SerializedName("state"        ) var state       : String?,

) {
    fun toLocation(): Location {
        return Location(
            name = name,
            lat = lat,
            lon = lon,
            country = country ?: "",
            state = state ?: "",
        )
    }
}