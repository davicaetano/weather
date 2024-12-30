package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.UnitSystem
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


data class ForecastDataResult (

    @SerializedName("cod"     ) var cod     : String?         = null,
    @SerializedName("message" ) var message : Int?            = null,
    @SerializedName("cnt"     ) var cnt     : Int?            = null,
    @SerializedName("list"    ) var listNM  : ArrayList<ListNM> = arrayListOf(),
    @SerializedName("city"    ) var cityNM  : CityNM,

) {
    fun toForecastList(unitSystem: UnitSystem): List<Forecast> {
        return this.listNM.map { listNM ->
            Forecast(
                temp = listNM.mainNM.temp,
                date = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(listNM.date.toLong() * 1000),
                    ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(cityNM.timezone))
                ),
                description = listNM.weatherNM.getOrNull(0)?.description ?: "",
                icon = listNM.weatherNM.getOrNull(0)?.icon ?: "",
                feelsLike = listNM.mainNM.feelsLike,
                high = listNM.mainNM.tempMax,
                low = listNM.mainNM.tempMin,
                pressure = listNM.mainNM.pressure,
                humidity = listNM.mainNM.humidity,
                visibility = listNM.visibility ?: BigDecimal.ZERO,
                clouds = listNM.cloudsNM.all,
                location = cityNM.name,
                sunrise = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(cityNM.sunrise.toLong() * 1000),
                    ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(cityNM.timezone))
                ),
                sunset = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(cityNM.sunset.toLong() * 1000),
                    ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(cityNM.timezone))
                ),
                unitSystem = unitSystem,
            )
        }
    }
}