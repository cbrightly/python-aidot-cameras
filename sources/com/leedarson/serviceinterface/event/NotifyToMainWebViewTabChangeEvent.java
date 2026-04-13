package com.leedarson.serviceinterface.event;

import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import com.meituan.robust.ChangeQuickRedirect;

public class NotifyToMainWebViewTabChangeEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String action;
    private String data;
    private OnBridgeRespListener listener;
    private String service;

    public NotifyToMainWebViewTabChangeEvent(String service2, String action2, String data2) {
        this.service = service2;
        this.action = action2;
        this.data = data2;
    }

    public NotifyToMainWebViewTabChangeEvent(String service2, String action2, String data2, OnBridgeRespListener listener2) {
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
