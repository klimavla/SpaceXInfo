
package com.example.vladi.spacexinfo.model.launch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fairings {

    @SerializedName("reused")
    @Expose
    private Object reused;
    @SerializedName("recovery_attempt")
    @Expose
    private Object recoveryAttempt;
    @SerializedName("recovered")
    @Expose
    private Object recovered;
    @SerializedName("ship")
    @Expose
    private Object ship;

    public Object getReused() {
        return reused;
    }

    public void setReused(Object reused) {
        this.reused = reused;
    }

    public Object getRecoveryAttempt() {
        return recoveryAttempt;
    }

    public void setRecoveryAttempt(Object recoveryAttempt) {
        this.recoveryAttempt = recoveryAttempt;
    }

    public Object getRecovered() {
        return recovered;
    }

    public void setRecovered(Object recovered) {
        this.recovered = recovered;
    }

    public Object getShip() {
        return ship;
    }

    public void setShip(Object ship) {
        this.ship = ship;
    }

}
