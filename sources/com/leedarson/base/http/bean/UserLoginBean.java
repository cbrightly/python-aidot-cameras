package com.leedarson.base.http.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class UserLoginBean extends UserTokenBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String defaultLocationId;
    private String mqttPassword;
    private String userId;
    private String venderCode;

    public String getVenderCode() {
        return this.venderCode;
    }

    public void setVenderCode(String venderCode2) {
        this.venderCode = venderCode2;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }

    public String getMqttPassword() {
        return this.mqttPassword;
    }

    public void setMqttPassword(String mqttPassword2) {
        this.mqttPassword = mqttPassword2;
    }

    public String getDefaultLocationId() {
        return this.defaultLocationId;
    }

    public void setDefaultLocationId(String defaultLocationId2) {
        this.defaultLocationId = defaultLocationId2;
    }
}
