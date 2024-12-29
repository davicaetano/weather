package com.davicaetano.weather.data.network.model

import com.google.gson.annotations.SerializedName


data class CityNM (

    @SerializedName("id"         ) var id         : Int,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("coord"      ) var coordNM      : CoordNM?  = null,
    @SerializedName("country"    ) var country    : String? = null,
    @SerializedName("population" ) var population : Int?    = null,
    @SerializedName("timezone"   ) var timezone   : Int,
    @SerializedName("sunrise"    ) var sunrise    : Int?    = null,
    @SerializedName("sunset"     ) var sunset     : Int?    = null

)