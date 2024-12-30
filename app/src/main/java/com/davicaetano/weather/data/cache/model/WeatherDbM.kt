package com.davicaetano.weather.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davicaetano.weather.model.UnitSystem
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "weather")
data class WeatherDbM(
    val lat: Double,
    val lon: Double,
    @PrimaryKey val id: String = "${lat} - ${lon}",
    val temp: BigDecimal,
    val date: LocalDateTime,
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
)
