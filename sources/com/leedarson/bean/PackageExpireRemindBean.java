package com.leedarson.bean;

import com.google.gson.annotations.SerializedName;

public class PackageExpireRemindBean {
    @SerializedName("code")
    public Integer code;
    @SerializedName("data")
    public DataBean data;
    @SerializedName("desc")
    public Object desc;

    public static class DataBean {
        @SerializedName("expireTime")
        public Long expireTime;
        @SerializedName("packageId")
        public String packageId;
        @SerializedName("showExpire")
        public Integer showExpire;
        @SerializedName("subscribeStatus")
        public int subscribeStatus;
    }
}
