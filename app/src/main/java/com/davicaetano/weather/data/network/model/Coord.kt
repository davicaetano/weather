package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class Coord (

  @SerializedName("lat" ) var lat : BigDecimal? = null,
  @SerializedName("lon" ) var lon : BigDecimal? = null

)