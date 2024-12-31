package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class WeatherNM (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("main"        ) var main        : String?,
    @SerializedName("description" ) var description : String?,
    @SerializedName("icon"        ) var icon        : String?

)