package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class SysWeatherNM (

    @SerializedName("country"    ) var country    : String? = null,
    @SerializedName("sunrise"    ) var sunrise    : Int = 0,
    @SerializedName("sunset"     ) var sunset     : Int = 0,

)