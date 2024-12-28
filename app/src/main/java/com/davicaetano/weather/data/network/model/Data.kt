package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class Data (

  @SerializedName("dt"         ) var dt         : Int?               = null,
  @SerializedName("main"       ) var main       : Main?              = Main(),
  @SerializedName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf(),
  @SerializedName("clouds"     ) var clouds     : Clouds?            = Clouds(),
  @SerializedName("wind"       ) var wind       : Wind?              = Wind(),
  @SerializedName("visibility" ) var visibility : BigDecimal?        = null,
  @SerializedName("pop"        ) var pop        : BigDecimal?        = null,
  @SerializedName("sys"        ) var sys        : Sys?               = Sys(),
  @SerializedName("dt_txt"     ) var dtTxt      : String?            = null

)