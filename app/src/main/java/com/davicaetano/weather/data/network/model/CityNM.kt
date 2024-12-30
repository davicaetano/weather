package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class CityNM (

    @SerializedName("id"         ) var id         : Int,
    @SerializedName("name"       ) var name       : String,
    @SerializedName("coord"      ) var coordNM    : CoordNM,
    @SerializedName("country"    ) var country    : String,
    @SerializedName("population" ) var population : Int,
    @SerializedName("timezone"   ) var timezone   : Int,
    @SerializedName("sunrise"    ) var sunrise    : Int,
    @SerializedName("sunset"     ) var sunset     : Int

)