package com.example.poc_voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("expiresIn")
    @Expose
    private Integer expiresIn;
    @SerializedName("expiresUnit")
    @Expose
    private String expiresUnit;
    @SerializedName("requiredPasswordUpdate")
    @Expose
    private Boolean requiredPasswordUpdate;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getExpiresUnit() {
        return expiresUnit;
    }

    public void setExpiresUnit(String expiresUnit) {
        this.expiresUnit = expiresUnit;
    }

    public Boolean getRequiredPasswordUpdate() {
        return requiredPasswordUpdate;
    }

    public void setRequiredPasswordUpdate(Boolean requiredPasswordUpdate) {
        this.requiredPasswordUpdate = requiredPasswordUpdate;
    }
}
