package com.example.vladi.spacexinfo.model.rocket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weight {

    @SerializedName("kg")
    @Expose
    private Double kg;
    @SerializedName("lb")
    @Expose
    private Double lb;

    public Double getKg() {
        return kg;
    }

    public void setKg(Double kg) {
        this.kg = kg;
    }

    public Double getLb() {
        return lb;
    }

    public void setLb(Double lb) {
        this.lb = lb;
    }

}
