package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class SysWeatherNM (

    @SerializedName("country"    ) var country    : String? = null,
    @SerializedName("sunrise"    ) var sunrise    : Int,
    @SerializedName("sunset"     ) var sunset     : Int,

)