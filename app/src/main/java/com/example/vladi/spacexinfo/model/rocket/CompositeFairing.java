package com.example.vladi.spacexinfo.model.rocket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompositeFairing {

    @SerializedName("height")
    @Expose
    private Size height;
    @SerializedName("diameter")
    @Expose
    private Size diameter;

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

}
