package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

public class DataBean {
    @SerializedName("ack")
    public AckBean ack;
    @SerializedName("code")
    public int code = 200;
    @SerializedName("data")
    public JSONObject data;
    @SerializedName("desc")
    public String desc = "";
    @SerializedName("devId")
    public String devId;
    @SerializedName("method")
    public String method;
    @SerializedName("payload")
    public PayloadBean payload;
    @SerializedName("seq")
    public String seq;
    @SerializedName("service")
    public String service;
    @SerializedName("srcAddr")
    public String srcAddr;
}
