package com.example.vladi.spacexinfo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vladi.spacexinfo.R;
import com.example.vladi.spacexinfo.interfaces.InfoApiInterface;
import com.example.vladi.spacexinfo.model.info.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment  extends Fragment implements Callback<Info> {

    private View mContent;
    private static final String BASE_URL = "https://api.spacexdata.com/";
    private static final String IMG_URL = "https://www.spacex.com/sites/spacex/files/styles/new_gallery_large/public/2016_-_11-its.jpg?itok=yTl_JcuG";
    private  LinearLayout containerLayout;

    public static Fragment newInstance() {
        Fragment frag = new HomeFragment();
        return frag;
    }

    private void add_row(String title, String value){
        View child = getLayoutInflater().inflate(R.layout.item_card, null);
        ((TextView)child.findViewById(R.id.item_name)).setText(title);
        ((TextView)child.findViewById(R.id.item_value)).setText(value);
        containerLayout.addView(child);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContent = view;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        InfoApiInterface rocketAPI = retrofit.create(InfoApiInterface.class);

        Call<Info> call = rocketAPI.loadInfo();
        call.enqueue(this);
        
        Glide.with(getContext()).load(IMG_URL).into((ImageView) mContent.findViewById(R.id.backdrop));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResponse(Call<Info> call, Response<Info> response) {
        Info info = response.body();
        CollapsingToolbarLayout collapsingToolbar = mContent.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(info.getName());

        containerLayout = mContent.findViewById(R.id.linear_container);

        add_row(getContext().getString(R.string.ceo),info.getCeo());
        add_row(getContext().getString(R.string.coo),info.getCoo());
        add_row(getContext().getString(R.string.cto),info.getCto());
        add_row(getContext().getString(R.string.cto_propulsion),info.getCtoPropulsion());
        add_row(getContext().getString(R.string.employees),info.getEmployees().toString());
        add_row(getContext().getString(R.string.vehicles),info.getVehicles().toString());
        add_row(getContext().getString(R.string.launch_sites),info.getLaunchSites().toString());
        add_row(getContext().getString(R.string.test_sites),info.getTestSites().toString());
        add_row(getContext().getString(R.string.founder),info.getFounder().toString());
        add_row(getContext().getString(R.string.founded),info.getFounded().toString());
        add_row(getContext().getString(R.string.valuation),info.getValuation().toString());
        add_row(getContext().getString(R.string.headquarters),info.getHeadquarters().getAddress() + ", " + info.getHeadquarters().getCity() + ", " + info.getHeadquarters().getState());


    }

    @Override
    public void onFailure(Call<Info> call, Throwable t) {

    }
}
