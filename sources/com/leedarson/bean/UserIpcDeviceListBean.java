package com.leedarson.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserIpcDeviceListBean {
    @SerializedName("code")
    public Integer code;
    @SerializedName("data")
    public List<DataBean> data;
    @SerializedName("desc")
    public Object desc;

    public static class DataBean {
        @SerializedName("bindStatus")
        public Integer bindStatus;
        @SerializedName("deviceId")
        public String deviceId;
        @SerializedName("deviceName")
        public String deviceName;
    }
}
