package com.example.vladi.spacexinfo.model.rocket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstStage {

    @SerializedName("reusable")
    @Expose
    private Boolean reusable;
    @SerializedName("engines")
    @Expose
    private Integer engines;
    @SerializedName("fuel_amount_tons")
    @Expose
    private Double fuelAmountTons;
    @SerializedName("burn_time_sec")
    @Expose
    private Double burnTimeSec;
    @SerializedName("thrust_sea_level")
    @Expose
    private Force thrustSeaLevel;
    @SerializedName("thrust_vacuum")
    @Expose
    private Force thrustVacuum;
    @SerializedName("cores")
    @Expose
    private Integer cores;

    public Boolean getReusable() {
        return reusable;
    }

    public void setReusable(Boolean reusable) {
        this.reusable = reusable;
    }

    public Integer getEngines() {
        return engines;
    }

    public void setEngines(Integer engines) {
        this.engines = engines;
    }

    public Double getFuelAmountTons() {
        return fuelAmountTons;
    }

    public void setFuelAmountTons(Double fuelAmountTons) {
        this.fuelAmountTons = fuelAmountTons;
    }

    public Double getBurnTimeSec() {
        return burnTimeSec;
    }

    public void setBurnTimeSec(Double burnTimeSec) {
        this.burnTimeSec = burnTimeSec;
    }

    public Force getThrustSeaLevel() {
        return thrustSeaLevel;
    }

    public void setThrustSeaLevel(Force thrustSeaLevel) {
        this.thrustSeaLevel = thrustSeaLevel;
    }

    public Force getThrustVacuum() {
        return thrustVacuum;
    }

    public void setThrustVacuum(Force thrustVacuum) {
        this.thrustVacuum = thrustVacuum;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

}
