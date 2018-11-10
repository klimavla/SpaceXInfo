package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Reuse {

    @SerializedName("core")
    @Expose
    var core: Boolean? = null
    @SerializedName("side_core1")
    @Expose
    var sideCore1: Boolean? = null
    @SerializedName("side_core2")
    @Expose
    var sideCore2: Boolean? = null
    @SerializedName("fairings")
    @Expose
    var fairings: Boolean? = null
    @SerializedName("capsule")
    @Expose
    var capsule: Boolean? = null

}
