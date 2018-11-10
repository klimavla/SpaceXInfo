package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Core {

    @SerializedName("core_serial")
    @Expose
    var coreSerial: String? = null
    @SerializedName("flight")
    @Expose
    var flight: Int? = null
    @SerializedName("block")
    @Expose
    var block: Int? = null
    @SerializedName("reused")
    @Expose
    var reused: Boolean? = null
    @SerializedName("land_success")
    @Expose
    var landSuccess: Boolean? = null
    @SerializedName("landing_type")
    @Expose
    var landingType: String? = null
    @SerializedName("landing_vehicle")
    @Expose
    var landingVehicle: String? = null

}
