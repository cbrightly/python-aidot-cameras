package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class SDPlayInfoBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String date;
    private String devId;
    private String deviceName;
    private String p2pId;
    private String password;
    private String playTime;
    private int type;

    public String getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(String playTime2) {
        this.playTime = playTime2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getDevId() {
        return this.devId;
    }

    public void setDevId(String devId2) {
        this.devId = devId2;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getP2pId() {
        return this.p2pId;
    }

    public void setP2pId(String p2pId2) {
        this.p2pId = p2pId2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
