package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IceServerBean {
    @SerializedName("code")
    public Integer code;
    @SerializedName("data")
    public List<DataBean> data;
    @SerializedName("desc")
    public String desc;

    public static class DataBean {
        @SerializedName("ack")
        public AckBean ack;
        @SerializedName("clientId")
        public String clientId;
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

        public static class AckBean {
            @SerializedName("code")
            public Integer code;
            @SerializedName("desc")
            public String desc;
        }

        public static class PayloadBean {
            @SerializedName("id")
            public String id;
            @SerializedName("stunList")
            public List<String> stunList;
            @SerializedName("token")
            public String token;
            @SerializedName("ttl")
            public Integer ttl;
            @SerializedName("turnList")
            public List<String> turnList;
        }
    }
}
