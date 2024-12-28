package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class Wind (

  @SerializedName("speed" ) var speed : BigDecimal? = null,
  @SerializedName("deg"   ) var deg   : BigDecimal? = null,
  @SerializedName("gust"  ) var gust  : BigDecimal? = null

)