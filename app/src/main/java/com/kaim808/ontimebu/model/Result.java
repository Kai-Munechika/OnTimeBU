package com.kaim808.ontimebu.model;

/**
 * Created by KaiM on 3/2/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("call_name")
    @Expose
    private String callName;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("coordinates")
    @Expose
    private String coordinates;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("general_heading")
    @Expose
    private Integer generalHeading;
    @SerializedName("arrival_estimates")
    @Expose
    private List<ArrivalEstimate> arrivalEstimates = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getGeneralHeading() {
        return generalHeading;
    }

    public void setGeneralHeading(Integer generalHeading) {
        this.generalHeading = generalHeading;
    }

    public List<ArrivalEstimate> getArrivalEstimates() {
        return arrivalEstimates;
    }

    public void setArrivalEstimates(List<ArrivalEstimate> arrivalEstimates) {
        this.arrivalEstimates = arrivalEstimates;
    }

}


