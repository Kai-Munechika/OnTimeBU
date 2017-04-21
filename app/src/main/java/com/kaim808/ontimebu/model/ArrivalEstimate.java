package com.kaim808.ontimebu.model;

/**
 * Created by KaiM on 3/2/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ArrivalEstimate {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("arrival_at")
    @Expose
    private String arrivalAt;
    @SerializedName("stop_id")
    @Expose
    private String stopId;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(String arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

}
