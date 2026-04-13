package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import java.io.Serializable;

public class DeviceBean implements Serializable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String account;
    private int alarmEnable = -1;
    private int alarmStatus = -1;
    private String audioCodec;
    private String deviceId;
    private String deviceStatusColor;
    private int digitZoom = -1;
    private boolean hasPTZ = false;
    private boolean hasPTZDown = true;
    private boolean hasPTZLeft = true;
    private boolean hasPTZRight = true;
    private boolean hasPTZUp = true;
    private int isDTLS = 0;
    private boolean isStandby = false;
    private boolean micEnable = true;
    private String micEnableTip = "";
    private String name;
    private String p2pId;
    private String password;
    private String previewUrl;
    private int rate = 8000;
    private int talkMode = 1;

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setPreviewUrl(String previewUrl2) {
        this.previewUrl = previewUrl2;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    public String getP2pId() {
        return this.p2pId;
    }

    public void setP2pId(String p2pId2) {
        this.p2pId = p2pId2;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account2) {
        this.account = account2;
    }

    public String getDeviceStatusColor() {
        return this.deviceStatusColor;
    }

    public void setDeviceStatusColor(String deviceStatusColor2) {
        this.deviceStatusColor = deviceStatusColor2;
    }

    public boolean isHasPTZ() {
        return this.hasPTZ;
    }

    public void setHasPTZ(boolean hasPTZ2) {
        this.hasPTZ = hasPTZ2;
    }

    public int getDigitZoom() {
        return this.digitZoom;
    }

    public void setDigitZoom(int digitZoom2) {
        this.digitZoom = digitZoom2;
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

    public boolean isStandby() {
        return this.isStandby;
    }

    public void setStandby(boolean standby) {
        this.isStandby = standby;
    }

    public boolean isMicEnable() {
        return this.micEnable;
    }

    public void setMicEnable(boolean micEnable2) {
        this.micEnable = micEnable2;
    }

    public String getMicEnableTip() {
        return this.micEnableTip;
    }

    public void setMicEnableTip(String micEnableTip2) {
        this.micEnableTip = micEnableTip2;
    }

    public boolean isHasPTZLeft() {
        return this.hasPTZLeft;
    }

    public void setHasPTZLeft(boolean hasPTZLeft2) {
        this.hasPTZLeft = hasPTZLeft2;
    }

    public boolean isHasPTZRight() {
        return this.hasPTZRight;
    }

    public void setHasPTZRight(boolean hasPTZRight2) {
        this.hasPTZRight = hasPTZRight2;
    }

    public boolean isHasPTZUp() {
        return this.hasPTZUp;
    }

    public void setHasPTZUp(boolean hasPTZUp2) {
        this.hasPTZUp = hasPTZUp2;
    }

    public boolean isHasPTZDown() {
        return this.hasPTZDown;
    }

    public void setHasPTZDown(boolean hasPTZDown2) {
        this.hasPTZDown = hasPTZDown2;
    }

    public String getAudioCodec() {
        return this.audioCodec;
    }

    public void setAudioCodec(String audioCodec2) {
        this.audioCodec = audioCodec2;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int rate2) {
        this.rate = rate2;
    }

    public int getIsDTLS() {
        return this.isDTLS;
    }

    public void setIsDTLS(int isDTLS2) {
        this.isDTLS = isDTLS2;
    }

    public int getTalkMode() {
        return this.talkMode;
    }

    public void setTalkMode(int talkMode2) {
        this.talkMode = talkMode2;
    }
}
