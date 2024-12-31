package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class SysForecastNM(

    @SerializedName("pod") var pod: String? = null

)