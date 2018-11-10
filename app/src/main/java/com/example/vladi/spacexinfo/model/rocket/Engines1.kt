package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Engines {

    @SerializedName("number")
    @Expose
    var number: Int? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("version")
    @Expose
    var version: String? = null
    @SerializedName("layout")
    @Expose
    var layout: String? = null
    @SerializedName("engine_loss_max")
    @Expose
    var engineLossMax: Double? = null
    @SerializedName("propellant_1")
    @Expose
    var propellant1: String? = null
    @SerializedName("propellant_2")
    @Expose
    var propellant2: String? = null
    @SerializedName("thrust_sea_level")
    @Expose
    var thrustSeaLevel: Force? = null
    @SerializedName("thrust_vacuum")
    @Expose
    var thrustVacuum: Force? = null
    @SerializedName("thrust_to_weight")
    @Expose
    var thrustToWeight: Double? = null

}
