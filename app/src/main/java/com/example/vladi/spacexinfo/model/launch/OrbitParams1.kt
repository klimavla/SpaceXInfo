package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrbitParams {

    @SerializedName("reference_system")
    @Expose
    var referenceSystem: String? = null
    @SerializedName("regime")
    @Expose
    var regime: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Any? = null
    @SerializedName("semi_major_axis_km")
    @Expose
    var semiMajorAxisKm: Double? = null
    @SerializedName("eccentricity")
    @Expose
    var eccentricity: Double? = null
    @SerializedName("periapsis_km")
    @Expose
    var periapsisKm: Double? = null
    @SerializedName("apoapsis_km")
    @Expose
    var apoapsisKm: Double? = null
    @SerializedName("inclination_deg")
    @Expose
    var inclinationDeg: Double? = null
    @SerializedName("period_min")
    @Expose
    var periodMin: Double? = null
    @SerializedName("lifespan_years")
    @Expose
    var lifespanYears: Any? = null
    @SerializedName("epoch")
    @Expose
    var epoch: String? = null
    @SerializedName("mean_motion")
    @Expose
    var meanMotion: Double? = null
    @SerializedName("raan")
    @Expose
    var raan: Double? = null

}
