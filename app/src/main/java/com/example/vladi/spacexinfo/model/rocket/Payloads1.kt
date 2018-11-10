package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Payloads {

    @SerializedName("option_1")
    @Expose
    var option1: String? = null
    @SerializedName("composite_fairing")
    @Expose
    var compositeFairing: CompositeFairing? = null
    @SerializedName("option_2")
    @Expose
    var option2: String? = null

}
