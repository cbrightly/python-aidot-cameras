package com.leedarson.serviceinterface.event;

import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import com.meituan.robust.ChangeQuickRedirect;

public class JsCallH5ByNativeEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String action;
    private String data;
    private OnBridgeRespListener listener;
    private String service;

    public JsCallH5ByNativeEvent(String service2, String action2, String data2) {
        this.service = service2;
        this.action = action2;
        this.data = data2;
    }

    public JsCallH5ByNativeEvent(String service2, String action2, String data2, OnBridgeRespListener listener2) {
        this.service = service2;
        this.action = action2;
        this.data = data2;
        this.listener = listener2;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service2) {
        this.service = service2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data2) {
        this.data = data2;
    }

    public OnBridgeRespListener getListener() {
        return this.listener;
    }

    public void setListener(OnBridgeRespListener listener2) {
        this.listener = listener2;
    }
}
