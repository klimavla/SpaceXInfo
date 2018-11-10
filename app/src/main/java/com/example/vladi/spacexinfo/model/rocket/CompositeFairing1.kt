package com.example.vladi.spacexinfo.model.rocket

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CompositeFairing {

    @SerializedName("height")
    @Expose
    var height: Size? = null
    @SerializedName("diameter")
    @Expose
    var diameter: Size? = null

}
