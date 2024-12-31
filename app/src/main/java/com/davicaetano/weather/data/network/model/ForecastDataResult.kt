package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.UnitSystem
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


data class ForecastDataResult(

    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var listNM: ArrayList<ListNM> = arrayListOf(),
    @SerializedName("city") var cityNM: CityNM = CityNM(),

    ) {
    fun toForecastList(
        lat: Double,
        lon: Double,
        unitSystem: UnitSystem
    ): List<Forecast> {
        return this.listNM.map { listNM ->
            Forecast(
                lat = lat,
                lon = lon,
                temp = listNM.mainNM?.temp ?: BigDecimal.ZERO,
                date = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli((listNM.date?.toLong() ?: 0) * 1000),
                    ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(cityNM.timezone ?: 0))
                ),
                description = listNM.weatherNM?.getOrNull(0)?.description ?: "",
                icon = listNM.weatherNM?.getOrNull(0)?.icon ?: "",
                feelsLike = listNM.mainNM?.feelsLike ?: BigDecimal.ZERO,
                high = listNM.mainNM?.tempMax ?: BigDecimal.ZERO,
                low = listNM.mainNM?.tempMin ?: BigDecimal.ZERO,
                pressure = listNM.mainNM?.pressure ?: BigDecimal.ZERO,
                humidity = listNM.mainNM?.humidity ?: BigDecimal.ZERO,
                visibility = listNM.visibility ?: BigDecimal.ZERO,
                clouds = listNM.cloudsNM?.all ?: BigDecimal.ZERO,
                windSpeed = listNM.windNM?.speed ?: BigDecimal.ZERO,
                windDeg = listNM.windNM?.deg ?: BigDecimal.ZERO,
                location = cityNM.name ?: "",
                sunrise = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli((cityNM.sunrise?.toLong() ?: 0) * 1000),
                    ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(cityNM.timezone ?: 0))
                ),
                sunset = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli((cityNM.sunset?.toLong() ?: 0) * 1000),
                    ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(cityNM.timezone ?: 0))
                ),
                unitSystem = unitSystem,
            )
        }
    }
}