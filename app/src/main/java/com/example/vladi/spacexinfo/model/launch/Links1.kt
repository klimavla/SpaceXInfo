package com.example.vladi.spacexinfo.model.launch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Links {

    @SerializedName("mission_patch")
    @Expose
    var missionPatch: String? = null
    @SerializedName("mission_patch_small")
    @Expose
    var missionPatchSmall: String? = null
    @SerializedName("reddit_campaign")
    @Expose
    var redditCampaign: String? = null
    @SerializedName("reddit_launch")
    @Expose
    var redditLaunch: String? = null
    @SerializedName("reddit_recovery")
    @Expose
    var redditRecovery: Any? = null
    @SerializedName("reddit_media")
    @Expose
    var redditMedia: String? = null
    @SerializedName("presskit")
    @Expose
    var presskit: String? = null
    @SerializedName("article_link")
    @Expose
    var articleLink: String? = null
    @SerializedName("wikipedia")
    @Expose
    var wikipedia: String? = null
    @SerializedName("video_link")
    @Expose
    var videoLink: String? = null

}
