package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Telemetry {

    @SerializedName("flight_club")
    @Expose
    var flightClub: String? = null

}
