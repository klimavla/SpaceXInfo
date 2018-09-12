package com.example.vladi.spacexinfo.interfaces;

import com.example.vladi.spacexinfo.model.launch.Launch;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LaunchApiInterface {
    @GET("v2/launches")
    Call<List<Launch>> loadLaunches(@Query("launch_year") String year, @Query("rocket_id") String rocket_id);
}
