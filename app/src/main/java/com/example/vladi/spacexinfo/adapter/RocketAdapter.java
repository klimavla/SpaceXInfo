package com.example.vladi.spacexinfo.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vladi.spacexinfo.R;
import com.example.vladi.spacexinfo.interfaces.RocketApiInterface;
import com.example.vladi.spacexinfo.model.rocket.Rocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RocketAdapter extends RecyclerView.Adapter<RocketAdapter.MyViewHolder>  implements Callback<List<Rocket>> {

    private Context mContext;
    private List<Rocket> rocketList;
    static final String BASE_URL = "https://api.spacexdata.com/";

    public RocketAdapterClickListener clickListener;

    public interface RocketAdapterClickListener {
        void recyclerViewClick(String rocketID);
    }

    public RocketAdapter(Context mContext, List<Rocket> rocketList, RocketAdapterClickListener clickLisener) {
        this.mContext = mContext;
        this.rocketList = rocketList;
        this.clickListener = clickLisener;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(mContext.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RocketApiInterface rocketAPI = retrofit.create(RocketApiInterface.class);

        Call<List<Rocket>> call = rocketAPI.loadRockets();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Rocket>> call, Response<List<Rocket>> response) {

        if(response.isSuccessful()) {
            rocketList.clear();
            List<Rocket> rockets = response.body();
            rocketList.addAll(rockets);
            for (Rocket rocket: rocketList ) {
                rocket.loadThumbnailUrl(this,mContext);
            }
            notifyDataSetChanged();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Rocket>> call, Throwable t) {
        t.printStackTrace();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rocket_card, parent, false);

        return new MyViewHolder(itemView,new MyViewHolder.RocketClickListener() {
            @Override
            public void rocketOnClick(String rocketID) {
                clickListener.recyclerViewClick(rocketID);
            }

        });
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Rocket rocket = rocketList.get(position);
        holder.title.setText(rocket.getName());
        holder.count.setText(rocket.getType());
        holder.rocketID = rocket.getId();
        // loading rocket cover using Glide library
        if(rocket.getThumbnailUrl() != null) {
            Glide.with(mContext).load(rocket.getThumbnailUrl()).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return rocketList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, count;
        public ImageView thumbnail;
        public RocketClickListener listener;
        public String rocketID;

        //listener passed to viewHolder
        public interface RocketClickListener {
            void rocketOnClick(String rocketID);
        }

        public MyViewHolder(View view,RocketClickListener listener) {
            super(view);
            title = view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);

            this.listener = listener;
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.rocketOnClick(this.rocketID);
        }
    }
}