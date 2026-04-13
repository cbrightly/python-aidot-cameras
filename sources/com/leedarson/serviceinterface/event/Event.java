package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class Event<T> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String action;
    private String callBackKey;
    private T data;
    private String key;

    public String getCallBackKey() {
        return this.callBackKey;
    }

    public void setCallBackKey(String callBackKey2) {
        this.callBackKey = callBackKey2;
    }

    public Event(String key2) {
        this.key = key2;
    }

    public Event(String key2, String action2) {
        this.key = key2;
        this.action = action2;
    }

    public Event(String key2, T data2) {
        this.key = key2;
        this.data = data2;
    }

    public Event(String key2, String callBackKey2, String action2, T data2) {
        this.key = key2;
        this.callBackKey = callBackKey2;
        this.action = action2;
        this.data = data2;
    }

    public Event(String key2, String action2, T data2) {
        this.key = key2;
        this.action = action2;
        this.data = data2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(int code) {
        this.key = this.key;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data2) {
        this.data = data2;
    }
}
