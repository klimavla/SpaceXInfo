package com.example.vladi.spacexinfo.interfaces

import com.example.vladi.spacexinfo.model.info.Info
import retrofit2.Call
import retrofit2.http.GET

interface InfoApiInterface {
    @GET("v2/info")
    fun loadInfo(): Call<Info>
}
