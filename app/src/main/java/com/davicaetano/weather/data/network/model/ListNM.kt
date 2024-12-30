package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ListNM (

    @SerializedName("dt"         ) var date         : Int,
    @SerializedName("main"       ) var mainNM       : MainNM,
    @SerializedName("weather"    ) var weatherNM    : ArrayList<WeatherNM> = arrayListOf(),
    @SerializedName("clouds"     ) var cloudsNM     : CloudsNM,
    @SerializedName("wind"       ) var windNM       : WindNM,
    @SerializedName("visibility" ) var visibility   : BigDecimal? = BigDecimal.ZERO,
    @SerializedName("pop"        ) var pop          : BigDecimal,
    @SerializedName("rain"       ) var rainNM       : RainNM? = null,
    @SerializedName("snow"       ) var snowNM       : SnowNM? = null,
    @SerializedName("sys"        ) var sys          : SysForecastNM,
    @SerializedName("dt_txt"     ) var dtTxt        : String,

)