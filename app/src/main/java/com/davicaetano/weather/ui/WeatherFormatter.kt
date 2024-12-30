package com.davicaetano.weather.ui

import java.math.BigDecimal
import java.text.DecimalFormat
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

    fun getFormattedBigDecimal(temp: BigDecimal): String {
        return tempFormatter.format(temp)
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