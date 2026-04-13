package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class PlayerConfigBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int PTZ = -1;
    private int[] PTZCammands;
    private Object ROI;
    private int alarmEnable = -1;
    private int alarmStatus = -1;
    private int autoTrackingStatus = -1;
    private String deviceId;
    private String deviceStatusColor;
    private int digitZoom = -1;
    private String imgCachePath;
    private int micEnable = -1;
    private String micEnableTip;
    private String previewUrl;
    private int[] resolution;
    private int sleepStatus = -1;
    private int standbyStatus = -1;
    private String tabbarColor;
    private int talkMode = -1;
    private String themeColor;
    private String title;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public int getStandbyStatus() {
        return this.standbyStatus;
    }

    public void setStandbyStatus(int standbyStatus2) {
        this.standbyStatus = standbyStatus2;
    }

    public String getThemeColor() {
        return this.themeColor;
    }

    public void setThemeColor(String themeColor2) {
        this.themeColor = themeColor2;
    }

    public String getTabbarColor() {
        return this.tabbarColor;
    }

    public void setTabbarColor(String tabbarColor2) {
        this.tabbarColor = tabbarColor2;
    }

    public int getPTZ() {
        return this.PTZ;
    }

    public void setPTZ(int PTZ2) {
        this.PTZ = PTZ2;
    }

    public String getImgCachePath() {
        return this.imgCachePath;
    }

    public void setImgCachePath(String imgCachePath2) {
        this.imgCachePath = imgCachePath2;
    }

    public Object getROI() {
        return this.ROI;
    }

    public void setROI(Object ROI2) {
        this.ROI = ROI2;
    }

    public String getDeviceStatusColor() {
        return this.deviceStatusColor;
    }

    public void setDeviceStatusColor(String deviceStatusColor2) {
        this.deviceStatusColor = deviceStatusColor2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public int getAutoTrackingStatus() {
        return this.autoTrackingStatus;
    }

    public void setAutoTrackingStatus(int autoTrackingStatus2) {
        this.autoTrackingStatus = autoTrackingStatus2;
    }

    public int getAlarmStatus() {
        return this.alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus2) {
        this.alarmStatus = alarmStatus2;
    }

    public int getAlarmEnable() {
        return this.alarmEnable;
    }

    public void setAlarmEnable(int alarmEnable2) {
        this.alarmEnable = alarmEnable2;
    }

    public int getDigitZoom() {
        return this.digitZoom;
    }

    public void setDigitZoom(int digitZoom2) {
        this.digitZoom = digitZoom2;
    }

    public int[] getResolution() {
        return this.resolution;
    }

    public void setResolution(int[] resolution2) {
        this.resolution = resolution2;
    }

    public int getMicEnable() {
        return this.micEnable;
    }

    public void setMicEnable(int micEnable2) {
        this.micEnable = micEnable2;
    }

    public String getMicEnableTip() {
        return this.micEnableTip;
    }

    public void setMicEnableTip(String micEnableTip2) {
        this.micEnableTip = micEnableTip2;
    }

    public int[] getPTZCammands() {
        return this.PTZCammands;
    }

    public void setPTZCammands(int[] PTZCammands2) {
        this.PTZCammands = PTZCammands2;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setPreviewUrl(String previewUrl2) {
        this.previewUrl = previewUrl2;
    }

    public int getTalkMode() {
        return this.talkMode;
    }

    public void setTalkMode(int talkMode2) {
        this.talkMode = talkMode2;
    }

    public int getSleepStatus() {
        return this.sleepStatus;
    }

    public void setSleepStatus(int sleepStatus2) {
        this.sleepStatus = sleepStatus2;
    }
}
