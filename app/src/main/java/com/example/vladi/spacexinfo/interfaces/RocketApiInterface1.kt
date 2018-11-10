package com.example.vladi.spacexinfo.interfaces

import com.example.vladi.spacexinfo.model.rocket.Rocket

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RocketApiInterface {
    @GET("v2/rockets/")
    fun loadRockets(): Call<List<Rocket>>

    @GET("v2/rockets/{rocket_id}")
    fun loadRocket(@Path("rocket_id") rocketId: String?): Call<Rocket>
}
