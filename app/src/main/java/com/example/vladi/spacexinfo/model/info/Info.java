package com.example.vladi.spacexinfo.model.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("founder")
    @Expose
    private String founder;
    @SerializedName("founded")
    @Expose
    private Integer founded;
    @SerializedName("employees")
    @Expose
    private Integer employees;
    @SerializedName("vehicles")
    @Expose
    private Integer vehicles;
    @SerializedName("launch_sites")
    @Expose
    private Integer launchSites;
    @SerializedName("test_sites")
    @Expose
    private Integer testSites;
    @SerializedName("ceo")
    @Expose
    private String ceo;
    @SerializedName("cto")
    @Expose
    private String cto;
    @SerializedName("coo")
    @Expose
    private String coo;
    @SerializedName("cto_propulsion")
    @Expose
    private String ctoPropulsion;
    @SerializedName("valuation")
    @Expose
    private Long valuation;
    @SerializedName("headquarters")
    @Expose
    private Headquarters headquarters;
    @SerializedName("summary")
    @Expose
    private String summary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Integer getFounded() {
        return founded;
    }

    public void setFounded(Integer founded) {
        this.founded = founded;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public Integer getVehicles() {
        return vehicles;
    }

    public void setVehicles(Integer vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getLaunchSites() {
        return launchSites;
    }

    public void setLaunchSites(Integer launchSites) {
        this.launchSites = launchSites;
    }

    public Integer getTestSites() {
        return testSites;
    }

    public void setTestSites(Integer testSites) {
        this.testSites = testSites;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getCto() {
        return cto;
    }

    public void setCto(String cto) {
        this.cto = cto;
    }

    public String getCoo() {
        return coo;
    }

    public void setCoo(String coo) {
        this.coo = coo;
    }

    public String getCtoPropulsion() {
        return ctoPropulsion;
    }

    public void setCtoPropulsion(String ctoPropulsion) {
        this.ctoPropulsion = ctoPropulsion;
    }

    public Long getValuation() {
        return valuation;
    }

    public void setValuation(Long valuation) {
        this.valuation = valuation;
    }

    public Headquarters getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(Headquarters headquarters) {
        this.headquarters = headquarters;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
