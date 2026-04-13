package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import java.util.List;

public class MutiCameraBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String allCamera;
    private List<DeviceBean> devices;
    private String key;
    private String title;
    private String titleColor;
    private String userId;

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getKey() {
        return this.key;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setDevices(List<DeviceBean> devices2) {
        this.devices = devices2;
    }

    public List<DeviceBean> getDevices() {
        return this.devices;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAllCamera(String allCamera2) {
        this.allCamera = allCamera2;
    }

    public String getAllCamera() {
        return this.allCamera;
    }

    public void setTitleColor(String titleColor2) {
        this.titleColor = titleColor2;
    }

    public String getTitleColor() {
        return this.titleColor;
    }
}
