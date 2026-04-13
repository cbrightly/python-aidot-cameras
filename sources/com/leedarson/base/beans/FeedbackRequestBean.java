package com.leedarson.base.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackRequestBean {
    public static final int feedbackSecondTypeOfDeviceCloudPlayError = 22;
    public static final int feedbackSecondTypeOfDeviceMissingEvent = 22;
    public static final int feedbackSecondTypeOfDeviceOffline = 21;
    public static final int feedbackSecondTypeOfDeviceReconnect = 22;
    public static final int feedbackTypeOfDeviceProblems = 7;
    public FeedbackDoneBean done = new FeedbackDoneBean();
    public HashMap<String, String> headers = new HashMap<>();
    public String key = "Business.upload";
    public ArrayList<String> paths = new ArrayList<>();
    public String sessionId = "";
}
