package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.Forecast
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


data class ListNM (

    @SerializedName("dt"         ) var date         : Int,
    @SerializedName("main"       ) var mainNM       : MainNM,
    @SerializedName("weather"    ) var weatherNM    : ArrayList<WeatherNM> = arrayListOf(),
    @SerializedName("clouds"     ) var cloudsNM     : CloudsNM,
    @SerializedName("wind"       ) var windNM       : WindNM,
    @SerializedName("visibility" ) var visibility   : BigDecimal,
    @SerializedName("pop"        ) var pop          : BigDecimal,
    @SerializedName("rain"       ) var rainNM       : RainNM? = null,
    @SerializedName("snow"       ) var snowNM       : SnowNM? = null,
    @SerializedName("sys"        ) var sys          : SysForecastNM,
    @SerializedName("dt_txt"     ) var dtTxt        : String,

    ) {
    fun toWeather(timezone: Int): Forecast {
        return Forecast(
            temp = mainNM.temp,
            date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date.toLong() * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone))
            ),
            description = weatherNM.getOrNull(0)?.description ?: "",
            icon = weatherNM.getOrNull(0)?.icon ?: "",
            clouds = cloudsNM.all,
            wind = windNM.toWind(),
        )
    }
}