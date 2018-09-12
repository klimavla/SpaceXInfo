package com.example.vladi.spacexinfo.interfaces;

import com.example.vladi.spacexinfo.model.rocket.Rocket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RocketApiInterface {
    @GET("v2/rockets/")
    Call<List<Rocket>> loadRockets();
    @GET("v2/rockets/{rocket_id}")
    Call<Rocket> loadRocket(@Path("rocket_id") String rocketId);
}
