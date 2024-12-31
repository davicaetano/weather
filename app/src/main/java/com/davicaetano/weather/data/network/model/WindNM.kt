package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class WindNM (

    @SerializedName("speed" ) var speed : BigDecimal = BigDecimal.ZERO,
    @SerializedName("deg"   ) var deg   : BigDecimal = BigDecimal.ZERO,
    @SerializedName("gust"  ) var gust  : BigDecimal? = BigDecimal.ZERO,

)