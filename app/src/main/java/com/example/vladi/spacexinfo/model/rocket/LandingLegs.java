package com.example.vladi.spacexinfo.model.rocket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LandingLegs {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("material")
    @Expose
    private String material;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
