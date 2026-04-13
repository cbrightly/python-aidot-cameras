package com.leedarson.smarthome.robust.beans;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class ELKRobustStepRecordBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int code = 200;
    public long duration = 0;
    public long end = 0;
    public String request;
    public String response;
    public long start = 0;
    public String step = "";

    public void startRequest(String request2, String step2) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{request2, step2}, this, changeQuickRedirect, false, 10712, clsArr, Void.TYPE).isSupported) {
            this.start = System.currentTimeMillis();
            this.request = request2;
            this.step = step2;
        }
    }

    public void endRequest(String response2) {
        if (!PatchProxy.proxy(new Object[]{response2}, this, changeQuickRedirect, false, 10713, new Class[]{String.class}, Void.TYPE).isSupported) {
            long currentTimeMillis = System.currentTimeMillis();
            this.end = currentTimeMillis;
            this.response = response2;
            this.duration = currentTimeMillis - this.start;
        }
    }

    public void endRequestException(String message, int code2) {
        if (!PatchProxy.proxy(new Object[]{message, new Integer(code2)}, this, changeQuickRedirect, false, 10714, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            long currentTimeMillis = System.currentTimeMillis();
            this.end = currentTimeMillis;
            this.code = code2;
            this.response = message;
            this.duration = currentTimeMillis - this.start;
        }
    }
}
