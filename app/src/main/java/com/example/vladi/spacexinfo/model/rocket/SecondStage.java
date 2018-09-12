package com.example.vladi.spacexinfo.model.rocket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecondStage {

    @SerializedName("engines")
    @Expose
    private Integer engines;
    @SerializedName("fuel_amount_tons")
    @Expose
    private Double fuelAmountTons;
    @SerializedName("burn_time_sec")
    @Expose
    private Double burnTimeSec;
    @SerializedName("thrust")
    @Expose
    private Force thrust;
    @SerializedName("payloads")
    @Expose
    private Payloads payloads;

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

    public Force getThrust() {
        return thrust;
    }

    public void setThrust(Force thrust) {
        this.thrust = thrust;
    }

    public Payloads getPayloads() {
        return payloads;
    }

    public void setPayloads(Payloads payloads) {
        this.payloads = payloads;
    }

}
