package com.kaim808.ontimebu.model;

/**
 * Created by KaiM on 3/2/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Root {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("ResultSet")
    @Expose
    private ResultSet resultSet;
    @SerializedName("totalResultsAvailable")
    @Expose
    private Integer totalResultsAvailable;
    @SerializedName("isMissingResults")
    @Expose
    private Integer isMissingResults;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Integer getTotalResultsAvailable() {
        return totalResultsAvailable;
    }

    public void setTotalResultsAvailable(Integer totalResultsAvailable) {
        this.totalResultsAvailable = totalResultsAvailable;
    }

    public Integer getIsMissingResults() {
        return isMissingResults;
    }

    public void setIsMissingResults(Integer isMissingResults) {
        this.isMissingResults = isMissingResults;
    }

}
