package com.example.vladi.spacexinfo.interfaces

import com.example.vladi.spacexinfo.model.launch.Launch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LaunchApiInterface {
    @GET("v2/launches")
    fun loadLaunches(@Query("launch_year") year: String?, @Query("rocket_id") rocket_id: String?): Call<List<Launch>>
}
