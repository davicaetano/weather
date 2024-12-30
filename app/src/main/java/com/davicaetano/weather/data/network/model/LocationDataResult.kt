package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class LocationDataResult(
    @SerializedName("name"         ) var name        : String,
    @SerializedName("lat"          ) var lat         : Double,
    @SerializedName("lon"          ) var lon         : Double,
    @SerializedName("country"      ) var country     : String,
    @SerializedName("state"        ) var state       : String,

)