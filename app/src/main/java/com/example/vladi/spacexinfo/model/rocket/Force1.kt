package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Force {

    @SerializedName("kN")
    @Expose
    var kn: Double? = null
    @SerializedName("lbf")
    @Expose
    var lbf: Double? = null

}
