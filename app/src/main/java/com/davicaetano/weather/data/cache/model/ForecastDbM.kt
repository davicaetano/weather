package com.davicaetano.weather.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.UnitSystem
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "forecast")
data class ForecastDbM(
    val lat: Double,
    val lon: Double,

    val temp: BigDecimal,
    val date: LocalDateTime,
    @PrimaryKey val id: String = "${lat} - ${lon} - ${date}",
    val description: String,
    val icon: String,
    val feelsLike: BigDecimal,
    val high: BigDecimal,
    val low: BigDecimal,
    val pressure: BigDecimal,
    val humidity: BigDecimal,
    val visibility: BigDecimal,
    val clouds: BigDecimal,
    val windSpeed: BigDecimal,
    val windDeg: BigDecimal,
    val location: String,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val unitSystem: UnitSystem,
) {
    fun toForecast(): Forecast {
        return Forecast(
            lat = this.lat,
            lon = this.lon,
            temp = this.temp,
            date = this.date,
            description = this.description,
            icon = this.icon,
            feelsLike = this.feelsLike,
            high = this.high,
            low = this.low,
            pressure = this.pressure,
            humidity = this.humidity,
            visibility = this.visibility,
            clouds = this.clouds,
            windSpeed = this.windSpeed,
            windDeg = this.windDeg,
            location = this.location,
            sunrise = this.sunrise,
            sunset = this.sunset,
            unitSystem = this.unitSystem,
        )
    }
}

fun Forecast.toForecastDbM(isLocal: Boolean): ForecastDbM {
    return ForecastDbM(
        lat = if (isLocal) 0.0 else this.lat,
        lon = if (isLocal) 0.0 else this.lon,
        temp = this.temp,
        date = this.date,
        description = this.description,
        icon = this.icon,
        feelsLike = this.feelsLike,
        high = this.high,
        low = this.low,
        pressure = this.pressure,
        humidity = this.humidity,
        visibility = this.visibility,
        clouds = this.clouds,
        windSpeed = this.windSpeed,
        windDeg = this.windDeg,
        location = this.location,
        sunrise = this.sunrise,
        sunset = this.sunset,
        unitSystem = this.unitSystem,
    )
}