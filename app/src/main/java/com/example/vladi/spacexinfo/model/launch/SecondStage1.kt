package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SecondStage {

    @SerializedName("block")
    @Expose
    var block: Int? = null
    @SerializedName("payloads")
    @Expose
    var payloads: List<Payload>? = null

}
