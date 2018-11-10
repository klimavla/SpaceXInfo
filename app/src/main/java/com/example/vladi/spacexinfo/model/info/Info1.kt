package com.example.vladi.spacexinfo.model.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Info {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("founder")
    @Expose
    var founder: String? = null
    @SerializedName("founded")
    @Expose
    var founded: Int? = null
    @SerializedName("employees")
    @Expose
    var employees: Int? = null
    @SerializedName("vehicles")
    @Expose
    var vehicles: Int? = null
    @SerializedName("launch_sites")
    @Expose
    var launchSites: Int? = null
    @SerializedName("test_sites")
    @Expose
    var testSites: Int? = null
    @SerializedName("ceo")
    @Expose
    var ceo: String? = null
    @SerializedName("cto")
    @Expose
    var cto: String? = null
    @SerializedName("coo")
    @Expose
    var coo: String? = null
    @SerializedName("cto_propulsion")
    @Expose
    var ctoPropulsion: String? = null
    @SerializedName("valuation")
    @Expose
    var valuation: Long? = null
    @SerializedName("headquarters")
    @Expose
    var headquarters: Headquarters? = null
    @SerializedName("summary")
    @Expose
    var summary: String? = null

}
