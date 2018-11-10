package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LandingLegs {

    @SerializedName("number")
    @Expose
    var number: Int? = null
    @SerializedName("material")
    @Expose
    var material: String? = null

}
