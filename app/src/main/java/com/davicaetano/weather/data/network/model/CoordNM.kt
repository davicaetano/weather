package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class CoordNM (

    @SerializedName("lat" ) var lat : Double = 0.0,
    @SerializedName("lon" ) var lon : Double = 0.0

)