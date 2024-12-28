package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class Clouds (

  @SerializedName("all" ) var all : BigDecimal? = null

)