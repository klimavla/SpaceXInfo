package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LaunchSite {

    @SerializedName("site_id")
    @Expose
    var siteId: String? = null
    @SerializedName("site_name")
    @Expose
    var siteName: String? = null
    @SerializedName("site_name_long")
    @Expose
    var siteNameLong: String? = null

}
