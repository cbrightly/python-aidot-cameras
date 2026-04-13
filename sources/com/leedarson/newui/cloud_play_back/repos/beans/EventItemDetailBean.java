package com.leedarson.newui.cloud_play_back.repos.beans;

import com.meituan.robust.ChangeQuickRedirect;

public class EventItemDetailBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String deviceId = "";
    private String deviceName = "";
    private String eventCode = "";
    private String eventTime = "";
    private String eventType = "";
    private String picUrl;

    public String getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(String eventCode2) {
        this.eventCode = eventCode2;
    }

    public String getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(String eventTime2) {
        this.eventTime = eventTime2;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType2) {
        this.eventType = eventType2;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl2) {
        this.picUrl = picUrl2;
    }
}
