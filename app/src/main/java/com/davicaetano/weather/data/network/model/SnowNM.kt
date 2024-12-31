package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class SnowNM(
    @SerializedName("1h") var oneHour: BigDecimal? = null,
    @SerializedName("3h") var threeHour: BigDecimal? = null,
)
