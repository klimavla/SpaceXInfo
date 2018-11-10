package com.example.vladi.spacexinfo.model.rocket

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirstStage {

    @SerializedName("reusable")
    @Expose
    var reusable: Boolean? = null
    @SerializedName("engines")
    @Expose
    var engines: Int? = null
    @SerializedName("fuel_amount_tons")
    @Expose
    var fuelAmountTons: Double? = null
    @SerializedName("burn_time_sec")
    @Expose
    var burnTimeSec: Double? = null
    @SerializedName("thrust_sea_level")
    @Expose
    var thrustSeaLevel: Force? = null
    @SerializedName("thrust_vacuum")
    @Expose
    var thrustVacuum: Force? = null
    @SerializedName("cores")
    @Expose
    var cores: Int? = null

}
