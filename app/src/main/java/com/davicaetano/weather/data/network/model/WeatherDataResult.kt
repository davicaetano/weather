package com.davicaetano.weather.data.network.model

import com.davicaetano.weather.model.UnitSystem
import com.davicaetano.weather.model.Weather
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

data class WeatherDataResult(

    @SerializedName("coord") var coordNM: CoordNM? = CoordNM(),
    @SerializedName("weather") var weatherNM: ArrayList<WeatherNM>? = arrayListOf(),
    @SerializedName("base") var base: String? = "",
    @SerializedName("main") var mainNM: MainNM? = MainNM(),
    @SerializedName("visibility") var visibility: BigDecimal? = BigDecimal.ZERO,
    @SerializedName("wind") var windNM: WindNM? = WindNM(),
    @SerializedName("clouds") var cloudsNM: CloudsNM? = CloudsNM(),
    @SerializedName("rain") var rainNM: RainNM? = null,
    @SerializedName("snow") var snowNM: SnowNM? = null,
    @SerializedName("dt") var date: Int? = 0,
    @SerializedName("sys") var sysWeatherNM: SysWeatherNM = SysWeatherNM(),
    @SerializedName("timezone") var timezone: Int? = 0,
    @SerializedName("name") var name: String? = "",
    @SerializedName("cod") var cod: Int? = 0,
) {

    fun toWeather(
        lat: Double,
        lon: Double,
        unitSystem: UnitSystem
    ): Weather {
        return Weather(
            lat = lat,
            lon = lon,
            temp = mainNM?.temp ?: BigDecimal.ZERO,
            date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli((date?.toLong() ?: 0) * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone ?: 0))
            ),
            description = weatherNM?.getOrNull(0)?.description ?: "",
            icon = weatherNM?.getOrNull(0)?.icon ?: "",
            feelsLike = mainNM?.feelsLike ?: BigDecimal.ZERO,
            high = mainNM?.tempMax ?: BigDecimal.ZERO,
            low = mainNM?.tempMin ?: BigDecimal.ZERO,
            pressure = mainNM?.pressure ?: BigDecimal.ZERO,
            humidity = mainNM?.humidity ?: BigDecimal.ZERO,
            visibility = visibility ?: BigDecimal.ZERO,
            clouds = cloudsNM?.all ?: BigDecimal.ZERO,
            windSpeed = windNM?.speed ?: BigDecimal.ZERO,
            windDeg = windNM?.deg ?: BigDecimal.ZERO,
            location = name ?: "",
            sunrise = LocalDateTime.ofInstant(
                Instant.ofEpochMilli((sysWeatherNM.sunrise?.toLong() ?: 0) * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone ?: 0))
            ),
            sunset = LocalDateTime.ofInstant(
                Instant.ofEpochMilli((sysWeatherNM.sunset?.toLong() ?: 0) * 1000),
                ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezone ?: 0))
            ),
            unitSystem = unitSystem,
        )
    }
}
