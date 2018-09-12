package com.example.vladi.spacexinfo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vladi.spacexinfo.R;
import com.example.vladi.spacexinfo.interfaces.RocketApiInterface;
import com.example.vladi.spacexinfo.model.rocket.Rocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Locale;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RocketDetailFragment extends Fragment implements Callback<Rocket> {


    private View mContent;
    private RecyclerView mRecycler;
    private LinearLayout containerLayout;
    static final String BASE_URL = "https://api.spacexdata.com/";
    static final String KEY_ROCKETID = "rocket_id";

    public static Fragment newInstance(String rocketId) {
        Fragment frag = new RocketDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ROCKETID,rocketId);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String rocketId ="";
        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            rocketId = args.getString(KEY_ROCKETID);
        } else {
        }

        // initialize views
        mContent = view;

        containerLayout = mContent.findViewById(R.id.linear_container);
        Gson gson = new GsonBuilder().setLenient().create();

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RocketApiInterface rocketAPI = retrofit.create(RocketApiInterface.class);

        Call<Rocket> call = rocketAPI.loadRocket(rocketId);
        call.enqueue(this);


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }

    private void add_row(String title, String value){
        View child = getLayoutInflater().inflate(R.layout.item_card, null);
        ((TextView)child.findViewById(R.id.item_name)).setText(title);
        ((TextView)child.findViewById(R.id.item_value)).setText(value);
        containerLayout.addView(child);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResponse(Call<Rocket> call, Response<Rocket> response) {
        Rocket rocket = response.body();
        CollapsingToolbarLayout collapsingToolbar = mContent.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(rocket.getName());
        SharedPreferences prefs = getContext().getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE);
        Glide.with(getContext()).load(prefs.getString(rocket.getId(),"")).into((ImageView) mContent.findViewById(R.id.backdrop));



        String countryCode = getResources().getConfiguration().locale.getCountry().toUpperCase();
        boolean imperial = false;
        switch (countryCode) {
            case "US":
            case "LR":
            case "MM":
                imperial = true;
        }
        add_row(getString(R.string.type),rocket.getType());
        add_row(getString(R.string.height),imperial?(rocket.getHeight().getFeet()+getString(R.string.feet)):(rocket.getHeight().getMeters()+getString(R.string.meter)));
        add_row(getString(R.string.diameter),imperial?(rocket.getDiameter().getFeet()+getString(R.string.feet)):(rocket.getDiameter().getMeters()+getString(R.string.meter)));
        add_row(getString(R.string.active),rocket.getActive()?getString(R.string.active):getString(R.string.active));
        add_row(getString(R.string.description),rocket.getDescription());
        add_row(getString(R.string.success_rate),rocket.getSuccessRatePct().toString());
        add_row(getString(R.string.first_flight),rocket.getFirstFlight());
        add_row(getString(R.string.country),rocket.getCountry());
        add_row(getString(R.string.company),rocket.getCompany());
    }

    @Override
    public void onFailure(Call<Rocket> call, Throwable t) {

    }
}