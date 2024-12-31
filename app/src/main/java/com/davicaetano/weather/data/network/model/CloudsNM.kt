package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class CloudsNM (

    @SerializedName("all" ) var all : BigDecimal = BigDecimal.ZERO

)