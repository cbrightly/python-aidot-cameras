package com.leedarson.smartcamera.kvswebrtc.utils;

import com.leedarson.base.utils.i;
import com.meituan.robust.ChangeQuickRedirect;

public class WebRtcRealTimeReportBean {
    public String ct;
    public long cts;
    public String message;
    public MSG_TYPE msgType;

    public enum MSG_TYPE {
        STUNPING,
        RTT,
        HEART_BEAT,
        FRAMEREPORT,
        NACK;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public WebRtcRealTimeReportBean(String message2, MSG_TYPE type) {
        this.message = "";
        this.cts = 0;
        this.ct = "";
        this.msgType = MSG_TYPE.STUNPING;
        this.cts = System.currentTimeMillis();
        this.message = message2;
        this.msgType = type;
        this.ct = i.a(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
    }
}
