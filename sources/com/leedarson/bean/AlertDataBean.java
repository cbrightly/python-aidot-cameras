package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class AlertDataBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String[] alerts;
    private String alertsColor;
    private String[] buttonBackgroundColor;
    private String[] buttonColor;
    private String msg;
    private String style;
    private String title;

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style2) {
        this.style = style2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public String getAlertsColor() {
        return this.alertsColor;
    }

    public void setAlertsColor(String alertsColor2) {
        this.alertsColor = alertsColor2;
    }

    public String[] getAlerts() {
        return this.alerts;
    }

    public void setAlerts(String[] alerts2) {
        this.alerts = alerts2;
    }

    public String[] getButtonColor() {
        return this.buttonColor;
    }

    public void setButtonColor(String[] buttonColor2) {
        this.buttonColor = buttonColor2;
    }

    public String[] getButtonBackgroundColor() {
        return this.buttonBackgroundColor;
    }

    public void setButtonBackgroundColor(String[] buttonBackgroundColor2) {
        this.buttonBackgroundColor = buttonBackgroundColor2;
    }
}
