package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.Unit
import com.davicaetano.weather.model.Weather
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

data class WeatherDataResult (

    @SerializedName("coord"      ) var coordNM      : CoordNM,
    @SerializedName("weather"    ) var weatherNM    : ArrayList<WeatherNM> = arrayListOf(),
    @SerializedName("base"       ) var base         : String,
    @SerializedName("main"       ) var mainNM       : MainNM,
    @SerializedName("visibility" ) var visibility   : BigDecimal,
    @SerializedName("wind"       ) var windNM       : WindNM,
    @SerializedName("clouds"     ) var cloudsNM     : CloudsNM,
    @SerializedName("rain"       ) var rainNM       : RainNM? = null,
    @SerializedName("snow"       ) var snowNM       : SnowNM? = null,
    @SerializedName("dt"         ) var date         : Int,
    @SerializedName("sys"        ) var sysWeatherNM : SysWeatherNM,
    @SerializedName("timezone"   ) var timezone     : Int,
    @SerializedName("name"       ) var name         : String,
    @SerializedName("cod"        ) var cod          : Int,
) {

    fun toWeather(unit: Unit): Weather {
        return Weather(
            temp = mainNM.temp,
            date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date.toLong() * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone))
            ),
            description = weatherNM.getOrNull(0)?.description ?: "",
            icon = weatherNM.getOrNull(0)?.icon ?: "",
            feelsLike = mainNM.feelsLike,
            high = mainNM.tempMax,
            low = mainNM.tempMin,
            pressure = mainNM.pressure,
            humidity = mainNM.humidity,
            visibility = visibility,
            clouds = cloudsNM.all,
            wind = windNM.toWind(),
            location = name,
            sunrise = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(sysWeatherNM.sunrise.toLong() * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone))
            ),
            sunset = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(sysWeatherNM.sunset.toLong() * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone))
            ),
            unit = unit,
        )
    }
}