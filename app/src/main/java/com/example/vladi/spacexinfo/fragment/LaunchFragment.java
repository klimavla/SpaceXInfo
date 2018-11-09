package com.example.vladi.spacexinfo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.vladi.spacexinfo.R;
import com.example.vladi.spacexinfo.adapter.LaunchAdapter;
import com.example.vladi.spacexinfo.interfaces.RocketApiInterface;
import com.example.vladi.spacexinfo.model.ServiceGenerator;
import com.example.vladi.spacexinfo.model.rocket.Rocket;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class LaunchFragment extends Fragment {


    private RecyclerView mRecycler;
    private ArrayAdapter<String> rocketAdapter;
    private ArrayList<String> ids ;
    private  boolean init = true;

    public static Fragment newInstance() {
        Fragment frag = new LaunchFragment();
        return frag;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ids = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_launch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycler = view.findViewById(R.id.recycler_view);
        final SharedPreferences prefs = getContext().getSharedPreferences( "com.example.vladi", Context.MODE_PRIVATE);

        ArrayList launchList = new ArrayList<>();
        final LaunchAdapter adapter = new LaunchAdapter(getContext(),launchList, "","");//launchList,prefs.getString("filter_year",""),prefs.getString("filter_rocket_id",""));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.addItemDecoration(new LaunchFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(adapter);
        Spinner spinnerYear = view.findViewById(R.id.yearSpinner);
        Spinner spinnerRocket = view.findViewById(R.id.rocketSpinner);
        List<String> list = new ArrayList<>();

        int max_year = Calendar.getInstance().get(Calendar.YEAR);
        list.add(getString(R.string.all));

        for(int year = max_year; year >= 2006; year--){
            list.add(""+year);
        }

        final ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        rocketAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        rocketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRocket.setAdapter(rocketAdapter);

        Gson gson = new Gson();

        String ids_string = prefs.getString("rocket_ids", "");
        String[] ids_helper = gson.fromJson(ids_string, String[].class);
        String[] names = gson.fromJson(prefs.getString("rocket_names", ""), String[].class);


        if(ids_helper == null){
            Thread t = new Thread(new RocketTask());
            t.start();
        }else{
            ids.addAll(Arrays.asList(ids_helper));
            rocketAdapter.clear();
            rocketAdapter.addAll(names);
            rocketAdapter.notifyDataSetChanged();
        }
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!init)
                adapter.setYear(yearAdapter.getItem(i));
                //prefs.edit().putString("filter_year",yearAdapter.getItem(i)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerRocket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!init)
                adapter.setRocketId(ids.get(i));
                init=false;
                //prefs.edit().putString("filter_rocket_id",rocketAdapter.getItem(i)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    class RocketTask implements Runnable {


        public RocketTask() {
        }

        @Override
        public void run() {
            RocketApiInterface taskService = ServiceGenerator.createService(RocketApiInterface.class);
            Call<List<Rocket>> call = taskService.loadRockets();
            try {
                List<Rocket> rocketList = call.execute().body();
                final ArrayList names = new ArrayList<>();
                ids.clear();
                ids.add(getActivity().getString(R.string.all));
                names.add(getActivity().getString(R.string.all));
                if(rocketList != null) {
                    for (Rocket rocket : rocketList) {
                        ids.add(rocket.getId());
                        names.add(rocket.getName());
                    }
                    SharedPreferences prefs = getContext().getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    prefs.edit().putString("rocket_ids", gson.toJson(ids)).apply();
                    prefs.edit().putString("rocket_names", gson.toJson(names)).apply();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                rocketAdapter.clear();
                                rocketAdapter.addAll(names);
                                rocketAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
