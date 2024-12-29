package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class MainNM (

    @SerializedName("temp"       ) var temp      : BigDecimal,
    @SerializedName("feels_like" ) var feelsLike : BigDecimal,
    @SerializedName("temp_min"   ) var tempMin   : BigDecimal,
    @SerializedName("temp_max"   ) var tempMax   : BigDecimal,
    @SerializedName("pressure"   ) var pressure  : BigDecimal,
    @SerializedName("sea_level"  ) var seaLevel  : BigDecimal,
    @SerializedName("grnd_level" ) var grndLevel : BigDecimal,
    @SerializedName("humidity"   ) var humidity  : BigDecimal,
    @SerializedName("temp_kf"    ) var tempKf    : BigDecimal,

)