package com.davicaetano.weather.data.cache.model

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {

    @TypeConverter
    fun bigDecimalToString(input: LocalDateTime): String {
        return input.toEpochSecond(ZoneOffset.UTC).toString()
    }

    @TypeConverter
    fun stringToBigDecimal(input: String): LocalDateTime {
        return LocalDateTime.ofEpochSecond(input.toLong(), 0,  ZoneOffset.UTC)
    }

}