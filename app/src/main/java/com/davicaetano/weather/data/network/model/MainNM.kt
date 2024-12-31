package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class MainNM (

    @SerializedName("temp"       ) var temp      : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("feels_like" ) var feelsLike : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("temp_min"   ) var tempMin   : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("temp_max"   ) var tempMax   : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("pressure"   ) var pressure  : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("sea_level"  ) var seaLevel  : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("grnd_level" ) var grndLevel : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("humidity"   ) var humidity  : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("temp_kf"    ) var tempKf    : BigDecimal? = BigDecimal.ZERO,

)