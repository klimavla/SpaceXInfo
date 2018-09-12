package com.example.vladi.spacexinfo.model.rocket;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.bumptech.glide.Glide;
import com.example.vladi.spacexinfo.adapter.RocketAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Rocket {

    @SerializedName("rocketid")
    @Expose
    private Integer rocketid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("stages")
    @Expose
    private Integer stages;
    @SerializedName("boosters")
    @Expose
    private Integer boosters;
    @SerializedName("cost_per_launch")
    @Expose
    private Integer costPerLaunch;
    @SerializedName("success_rate_pct")
    @Expose
    private Integer successRatePct;
    @SerializedName("first_flight")
    @Expose
    private String firstFlight;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("height")
    @Expose
    private Size height;
    @SerializedName("diameter")
    @Expose
    private Size diameter;
    @SerializedName("mass")
    @Expose
    private Weight mass;
    @SerializedName("payload_weights")
    @Expose
    private List<PayloadWeight> payloadWeights = null;
    @SerializedName("first_stage")
    @Expose
    private FirstStage firstStage;
    @SerializedName("second_stage")
    @Expose
    private SecondStage secondStage;
    @SerializedName("engines")
    @Expose
    private Engines engines;
    @SerializedName("landing_legs")
    @Expose
    private LandingLegs landingLegs;
    @SerializedName("wikipedia")
    @Expose
    private String wikipedia;
    @SerializedName("description")
    @Expose
    private String description;
    private String thumbnailUrl;

    public Integer getRocketid() {
        return rocketid;
    }

    public void setRocketid(Integer rocketid) {
        this.rocketid = rocketid;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getStages() {
        return stages;
    }

    public void setStages(Integer stages) {
        this.stages = stages;
    }

    public Integer getBoosters() {
        return boosters;
    }

    public void setBoosters(Integer boosters) {
        this.boosters = boosters;
    }

    public Integer getCostPerLaunch() {
        return costPerLaunch;
    }

    public void setCostPerLaunch(Integer costPerLaunch) {
        this.costPerLaunch = costPerLaunch;
    }

    public Integer getSuccessRatePct() {
        return successRatePct;
    }

    public void setSuccessRatePct(Integer successRatePct) {
        this.successRatePct = successRatePct;
    }

    public String getFirstFlight() {
        return firstFlight;
    }

    public void setFirstFlight(String firstFlight) {
        this.firstFlight = firstFlight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Size getHeight() {
        return height;
    }

    public void setHeight(Size height) {
        this.height = height;
    }

    public Size getDiameter() {
        return diameter;
    }

    public void setDiameter(Size diameter) {
        this.diameter = diameter;
    }

    public Weight getMass() {
        return mass;
    }

    public void setMass(Weight mass) {
        this.mass = mass;
    }

    public List<PayloadWeight> getPayloadWeights() {
        return payloadWeights;
    }

    public void setPayloadWeights(List<PayloadWeight> payloadWeights) {
        this.payloadWeights = payloadWeights;
    }

    public FirstStage getFirstStage() {
        return firstStage;
    }

    public void setFirstStage(FirstStage firstStage) {
        this.firstStage = firstStage;
    }

    public SecondStage getSecondStage() {
        return secondStage;
    }

    public void setSecondStage(SecondStage secondStage) {
        this.secondStage = secondStage;
    }

    public Engines getEngines() {
        return engines;
    }

    public void setEngines(Engines engines) {
        this.engines = engines;
    }

    public LandingLegs getLandingLegs() {
        return landingLegs;
    }

    public void setLandingLegs(LandingLegs landingLegs) {
        this.landingLegs = landingLegs;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void loadThumbnailUrl(RocketAdapter rocketAdapter, Context mContext) {
        // HOLD A REFERENCE TO THE ADAPTER

        if (wikipedia != null && !wikipedia.equals("")) {

            new RocketTask(rocketAdapter, mContext).execute(wikipedia);
        }
    }
    public class RocketTask extends AsyncTask<String, Void, String> {
        private RocketAdapter adapter;
        private Context mContext;

        public RocketTask(RocketAdapter adapter, Context mContext) {
            this.adapter = adapter;
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url ;
            SharedPreferences prefs = mContext.getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE);
            url = prefs.getString(getId(),"");

            long date = prefs.getLong(getId()+"_date",new Date().getTime());

            long diffInMillies = new Date().getTime() - date;
            long days = TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);

            if(url.equals("") || days > 7) {
                String wiki_html = load_url(strings[0]);
                Document doc = Jsoup.parse(wiki_html);
                Elements pngs = doc.select("img[src$=.jpg]");
                url = "https:"  + pngs.first().attr("src");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(getId(),url);
                editor.putLong(getId()+ "_date" , date);
                editor.apply();
            }
            return url;
        }

        private String load_url(String ur) {
            String data = "";
            try {
                URL url = new URL(ur);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                urlConnection.disconnect();
                data = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String url) {
            setThumbnailUrl(url);
            adapter.notifyDataSetChanged();
        }
    }

}
