package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class ForecastDataResult (

    @SerializedName("cod"     ) var cod     : String?         = null,
    @SerializedName("message" ) var message : Int?            = null,
    @SerializedName("cnt"     ) var cnt     : Int?            = null,
    @SerializedName("list"    ) var listNM  : ArrayList<ListNM> = arrayListOf(),
    @SerializedName("city"    ) var cityNM  : CityNM,

)