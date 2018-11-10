package com.example.vladi.spacexinfo.model.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Headquarters {

    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null

}
