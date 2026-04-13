package com.leedarson.base.beans;

import java.util.HashMap;
import org.glassfish.grizzly.http.server.Constants;

public class FeedbackDoneBean {
    public HashMap<String, String> header = new HashMap<>();
    public String method = Constants.POST;
    public FeedbackDoneParamsBean params = new FeedbackDoneParamsBean();
    public String url = "";
}
