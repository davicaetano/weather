package com.davicaetano.weather.ui

import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherFormatter @Inject constructor(

) {

    private val tempFormatter = DecimalFormat("#")
    private val dateFormat = DateTimeFormatter.ofPattern("h:mm a")
    private val shortDateFormat = DateTimeFormatter.ofPattern("h a")

    private val shortDayOfTheWeek = DateTimeFormatter.ofPattern("")

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

    fun getShortDayOfTheWeek(date: LocalDateTime): String {
        return date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }

    fun getShortHour(date: LocalDateTime): String {
        return shortDateFormat.format(date)
    }
}