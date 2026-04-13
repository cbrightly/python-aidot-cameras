package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SdpAnswerBean {
    @SerializedName("code")
    public Integer code;
    @SerializedName("data")
    public List<DataBean> data;
    @SerializedName("desc")
    public String desc;
}
