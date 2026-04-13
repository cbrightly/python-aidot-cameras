package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IceConfigBean {
    @SerializedName("app")
    public List<AppBean> app;
    @SerializedName("dev")
    public List<DevBean> dev;
    @SerializedName("time")
    public Integer time;

    public static class AppBean {
        @SerializedName("dnsUris")
        public List<String> dnsUris;
        @SerializedName("id")
        public String id;
        @SerializedName("token")
        public String token;
        @SerializedName("ttl")
        public Integer ttl;
        @SerializedName("uris")
        public List<String> uris;
    }

    public static class DevBean {
        @SerializedName("dnsUris")
        public List<String> dnsUris;
        @SerializedName("id")
        public String id;
        @SerializedName("token")
        public String token;
        @SerializedName("ttl")
        public Integer ttl;
        @SerializedName("uris")
        public List<String> uris;
    }
}
