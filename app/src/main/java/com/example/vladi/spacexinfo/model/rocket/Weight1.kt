package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Weight {

    @SerializedName("kg")
    @Expose
    var kg: Double? = null
    @SerializedName("lb")
    @Expose
    var lb: Double? = null

}
