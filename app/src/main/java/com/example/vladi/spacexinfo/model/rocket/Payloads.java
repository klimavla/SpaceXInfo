package com.example.vladi.spacexinfo.model.rocket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payloads {

    @SerializedName("option_1")
    @Expose
    private String option1;
    @SerializedName("composite_fairing")
    @Expose
    private CompositeFairing compositeFairing;
    @SerializedName("option_2")
    @Expose
    private String option2;

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public CompositeFairing getCompositeFairing() {
        return compositeFairing;
    }

    public void setCompositeFairing(CompositeFairing compositeFairing) {
        this.compositeFairing = compositeFairing;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

}
