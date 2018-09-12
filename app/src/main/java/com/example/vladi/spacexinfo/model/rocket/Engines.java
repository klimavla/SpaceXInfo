package com.example.vladi.spacexinfo.model.rocket;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Engines {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("layout")
    @Expose
    private String layout;
    @SerializedName("engine_loss_max")
    @Expose
    private Double engineLossMax;
    @SerializedName("propellant_1")
    @Expose
    private String propellant1;
    @SerializedName("propellant_2")
    @Expose
    private String propellant2;
    @SerializedName("thrust_sea_level")
    @Expose
    private Force thrustSeaLevel;
    @SerializedName("thrust_vacuum")
    @Expose
    private Force thrustVacuum;
    @SerializedName("thrust_to_weight")
    @Expose
    private Double thrustToWeight;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Double getEngineLossMax() {
        return engineLossMax;
    }

    public void setEngineLossMax(Double engineLossMax) {
        this.engineLossMax = engineLossMax;
    }

    public String getPropellant1() {
        return propellant1;
    }

    public void setPropellant1(String propellant1) {
        this.propellant1 = propellant1;
    }

    public String getPropellant2() {
        return propellant2;
    }

    public void setPropellant2(String propellant2) {
        this.propellant2 = propellant2;
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

    public Double getThrustToWeight() {
        return thrustToWeight;
    }

    public void setThrustToWeight(Double thrustToWeight) {
        this.thrustToWeight = thrustToWeight;
    }

}
