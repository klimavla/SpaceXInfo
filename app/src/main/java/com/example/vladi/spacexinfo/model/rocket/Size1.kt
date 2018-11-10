package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Size {

    @SerializedName("meters")
    @Expose
    var meters: Double? = null
    @SerializedName("feet")
    @Expose
    var feet: Double? = null

}
