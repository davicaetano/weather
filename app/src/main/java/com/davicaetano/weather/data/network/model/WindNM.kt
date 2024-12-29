package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.Wind
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class WindNM (

    @SerializedName("speed" ) var speed : BigDecimal,
    @SerializedName("deg"   ) var deg   : BigDecimal,
    @SerializedName("gust"  ) var gust  : BigDecimal?,

) {
    fun toWind(): Wind {
        return Wind(
            speed,
            deg,
            gust
        )
    }
}