package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class CityNM(

    @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = "",
    @SerializedName("coord") var coordNM: CoordNM? = CoordNM(),
    @SerializedName("country") var country: String? = "",
    @SerializedName("population") var population: Int? = 0,
    @SerializedName("timezone") var timezone: Int? = 0,
    @SerializedName("sunrise") var sunrise: Int? = 0,
    @SerializedName("sunset") var sunset: Int? = 0

)