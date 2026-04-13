package com.leedarson.base.beans;

import java.util.ArrayList;

public class FeedbackDoneParamsBean {
    public String appVersion = "";
    public String code = "";
    public String content = "";
    public String country = "";
    public ArrayList<String> deviceIds = new ArrayList<>();
    public int feedbackSecondType = -1;
    public int feedbackType = -1;
    public ArrayList<String> fileIds = new ArrayList<>();
    public String modelName = "";
    public String nativeVersion = "";
    public long occurredTime = System.currentTimeMillis();
    public String os = "";
    public String osVersion = "";
    public String prePage = "";
    public String prePageTime = "";
    public long timestamp = 0;
    public String webVersion = "";
}
