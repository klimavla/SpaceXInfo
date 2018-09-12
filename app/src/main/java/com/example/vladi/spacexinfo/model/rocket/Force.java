package com.example.vladi.spacexinfo.model.rocket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Force {

    @SerializedName("kN")
    @Expose
    private Double kN;
    @SerializedName("lbf")
    @Expose
    private Double lbf;

    public Double getKN() {
        return kN;
    }

    public void setKN(Double kN) {
        this.kN = kN;
    }

    public Double getLbf() {
        return lbf;
    }

    public void setLbf(Double lbf) {
        this.lbf = lbf;
    }

}
