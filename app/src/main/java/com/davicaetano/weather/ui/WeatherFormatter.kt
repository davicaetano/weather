package com.davicaetano.weather.ui

import com.davicaetano.weather.model.Unit
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherFormatter @Inject constructor(

) {

    private val tempFormatter = DecimalFormat("#")
    private val dateFormat = DateTimeFormatter.ofPattern("hh:mm a")

    fun getTempWithoutDecimal(temp: BigDecimal): String {
        return tempFormatter.format(temp)
    }

    fun getPressure(pressure: BigDecimal): String {
        return tempFormatter.format(pressure)
    }

    fun getClouds(clouds: BigDecimal): String {
        return tempFormatter.format(clouds)
    }

    fun getHumidity(humidity: BigDecimal): String {
        return tempFormatter.format(humidity)
    }

    fun getTime(date: LocalDateTime): String {
        return dateFormat.format(date)
    }
}