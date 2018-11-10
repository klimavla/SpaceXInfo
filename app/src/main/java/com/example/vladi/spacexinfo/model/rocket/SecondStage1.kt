package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SecondStage {

    @SerializedName("engines")
    @Expose
    var engines: Int? = null
    @SerializedName("fuel_amount_tons")
    @Expose
    var fuelAmountTons: Double? = null
    @SerializedName("burn_time_sec")
    @Expose
    var burnTimeSec: Double? = null
    @SerializedName("thrust")
    @Expose
    var thrust: Force? = null
    @SerializedName("payloads")
    @Expose
    var payloads: Payloads? = null

}
