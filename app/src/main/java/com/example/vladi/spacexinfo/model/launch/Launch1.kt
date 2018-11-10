package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Launch {

    @SerializedName("flight_number")
    @Expose
    var flightNumber: Int? = null
    @SerializedName("mission_name")
    @Expose
    var missionName: String? = null
    @SerializedName("launch_year")
    @Expose
    var launchYear: String? = null
    @SerializedName("launch_date_unix")
    @Expose
    var launchDateUnix: Int? = null
    @SerializedName("launch_date_utc")
    @Expose
    var launchDateUtc: String? = null
    @SerializedName("launch_date_local")
    @Expose
    var launchDateLocal: String? = null
    @SerializedName("is_tentative")
    @Expose
    var isTentative: Boolean? = null
    @SerializedName("tentative_max_precision")
    @Expose
    var tentativeMaxPrecision: String? = null
    @SerializedName("rocket")
    @Expose
    var rocket: Rocket? = null
    @SerializedName("ships")
    @Expose
    var ships: List<String>? = null
    @SerializedName("reuse")
    @Expose
    var reuse: Reuse? = null
    @SerializedName("telemetry")
    @Expose
    var telemetry: Telemetry? = null
    @SerializedName("launch_site")
    @Expose
    var launchSite: LaunchSite? = null
    @SerializedName("launch_success")
    @Expose
    var launchSuccess: Boolean? = null
    @SerializedName("links")
    @Expose
    var links: Links? = null
    @SerializedName("details")
    @Expose
    var details: String? = null
    @SerializedName("upcoming")
    @Expose
    var upcoming: Boolean? = null
    @SerializedName("static_fire_date_utc")
    @Expose
    var staticFireDateUtc: String? = null

}
