package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class CoordNM (

    @SerializedName("lat" ) var lat : Double,
    @SerializedName("lon" ) var lon : Double

)