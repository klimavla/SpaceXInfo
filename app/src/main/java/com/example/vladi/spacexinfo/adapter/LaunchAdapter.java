package com.example.vladi.spacexinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vladi.spacexinfo.R;
import com.example.vladi.spacexinfo.interfaces.LaunchApiInterface;
import com.example.vladi.spacexinfo.model.launch.Launch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchAdapter.MyViewHolder>  implements Callback<List<Launch>> {

    private Context mContext;
    private List<Launch> launchList;
    private String year;
    private String rocketId;
    private DateFormat utcFormat;
    private DateFormat showFormat;
    private LaunchApiInterface launchApiInterface;
    static final String BASE_URL = "https://api.spacexdata.com/";

    @Override
    public void onResponse(Call<List<Launch>> call, Response<List<Launch>> response) {

        Log.w("LaunchAdapter", "response" );
        if(response.isSuccessful()) {
            launchList.clear();
            List<Launch> launches = response.body();
            launchList.addAll(launches);

            notifyDataSetChanged();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Launch>> call, Throwable t) {
        t.printStackTrace();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mission_name, flight_number,date,rocket;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            mission_name = view.findViewById(R.id.mission_name);
            flight_number = view.findViewById(R.id.flight_number);
            date = view.findViewById(R.id.date);
            rocket = view.findViewById(R.id.rocket);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public void addAll(List<Launch> launchList) {
        this.launchList.clear();
        this.launchList.addAll(launchList);
        this.notifyDataSetChanged();
    }

    public void setRocketId(String id){
        rocketId = id;
        load_data(year,rocketId);
    }

    public void setYear(String year){
        this.year = year;
        load_data(year,rocketId);
    }

    public LaunchAdapter(Context mContext, List<Launch> launchList, String year, String rocket) {
        this.mContext = mContext;
        this.launchList = launchList;
        this.rocketId = rocket;
        this.year = year;
        this.utcFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//2008-09-28T23:15:00.000Z
        this.showFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        init_retrofit();
        load_data(year,rocket);
    }

    private void init_retrofit(){
        Gson gson = new GsonBuilder().setLenient().create();

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(mContext.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        launchApiInterface = retrofit.create(LaunchApiInterface.class);
    }

    private void load_data(String year, String rocket){
        if(mContext.getString(R.string.all).equals(year)) year = "";
        if(mContext.getString(R.string.all).equals(rocketId)) rocketId = "";
        String url = launchApiInterface.loadLaunches(year,rocketId).request().url().toString();
        Call<List<Launch>> call = launchApiInterface.loadLaunches(year,rocket);
        call.enqueue(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.launch_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LaunchAdapter.MyViewHolder holder, int position) {
        Launch launch = launchList.get(position);
        holder.mission_name.setText(launch.getMissionName());
        Date newDate= null;
        String date = launch.getLaunchDateUtc();
        try {
            newDate = utcFormat.parse(launch.getLaunchDateUtc());
            date = showFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.flight_number.setText("#"+String.valueOf(launch.getFlightNumber()));
        holder.date.setText(date);
        holder.rocket.setText(launch.getRocket().getRocketName());
        Glide.with(mContext).load(launch.getLinks().getMissionPatchSmall()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }
}