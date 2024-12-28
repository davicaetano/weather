package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class WeatherDataResult (

  @SerializedName("cod"     ) var cod     : String?         = null,
  @SerializedName("message" ) var message : Int?            = null,
  @SerializedName("cnt"     ) var cnt     : Int?            = null,
  @SerializedName("list"    ) var data    : ArrayList<Data> = arrayListOf(),
  @SerializedName("city"    ) var city    : City?           = City()

)