package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class Main (

  @SerializedName("temp"       ) var temp      : BigDecimal? = null,
  @SerializedName("feels_like" ) var feelsLike : BigDecimal? = null,
  @SerializedName("temp_min"   ) var tempMin   : BigDecimal? = null,
  @SerializedName("temp_max"   ) var tempMax   : BigDecimal? = null,
  @SerializedName("pressure"   ) var pressure  : BigDecimal? = null,
  @SerializedName("sea_level"  ) var seaLevel  : BigDecimal? = null,
  @SerializedName("grnd_level" ) var grndLevel : BigDecimal? = null,
  @SerializedName("humidity"   ) var humidity  : BigDecimal? = null,
  @SerializedName("temp_kf"    ) var tempKf    : BigDecimal? = null

)