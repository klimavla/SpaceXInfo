package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Payload {

    @SerializedName("payload_id")
    @Expose
    var payloadId: String? = null
    @SerializedName("norad_id")
    @Expose
    var noradId: List<Int>? = null
    @SerializedName("reused")
    @Expose
    var reused: Boolean? = null
    @SerializedName("customers")
    @Expose
    var customers: List<String>? = null
    @SerializedName("nationality")
    @Expose
    var nationality: String? = null
    @SerializedName("manufacturer")
    @Expose
    var manufacturer: String? = null
    @SerializedName("payload_type")
    @Expose
    var payloadType: String? = null
    @SerializedName("payload_mass_kg")
    @Expose
    var payloadMassKg: Double? = null
    @SerializedName("payload_mass_lbs")
    @Expose
    var payloadMassLbs: Double? = null
    @SerializedName("orbit")
    @Expose
    var orbit: String? = null
    @SerializedName("orbit_params")
    @Expose
    var orbitParams: OrbitParams? = null
    @SerializedName("cap_serial")
    @Expose
    var capSerial: String? = null
    @SerializedName("mass_returned_kg")
    @Expose
    var massReturnedKg: Double? = null
    @SerializedName("mass_returned_lbs")
    @Expose
    var massReturnedLbs: Double? = null
    @SerializedName("flight_time_sec")
    @Expose
    var flightTimeSec: Double? = null
    @SerializedName("cargo_manifest")
    @Expose
    var cargoManifest: String? = null

}
