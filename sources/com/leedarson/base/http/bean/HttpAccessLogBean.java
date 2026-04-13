package com.leedarson.base.http.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class HttpAccessLogBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public long duration = 0;
    public String host = "";
    public int httpCode = 200;
    public String response = "";
    public long responseTime = 0;
    public long startCheckTime = 0;
    public String traceId = "";
    public String url = "";

    public boolean checkAvailable() {
        return this.httpCode == 200;
    }
}
