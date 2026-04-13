package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;

public class PayloadBean {
    @SerializedName("offer")
    public OfferBean offer;
    @SerializedName("peerid")
    public String peerid;
    @SerializedName("pskEnable")
    public int pskEnable;
    @SerializedName("supportRtpExt")
    public int supportRtpExt;
    @SerializedName("trackId")
    public int trackId;

    public static class OfferBean {
        @SerializedName("sdp")
        public String sdp;
        @SerializedName("type")
        public String type;
    }
}
