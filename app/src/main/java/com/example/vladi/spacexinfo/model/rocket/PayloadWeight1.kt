package com.example.vladi.spacexinfo.model.rocket


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PayloadWeight {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("kg")
    @Expose
    var kg: Int? = null
    @SerializedName("lb")
    @Expose
    var lb: Int? = null

}
