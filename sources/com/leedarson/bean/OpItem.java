package com.leedarson.bean;

import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.meituan.robust.ChangeQuickRedirect;

public enum OpItem {
    record("Record", R$drawable.ic_record, R$drawable.ic_live_record, 0, R$drawable.ic_record_disable),
    snap("Snap", R$drawable.ic_snap, 0, 0, R$drawable.ic_snap_disable),
    speakHalf("Speak", r21, r22, r2, R$drawable.ic_speak_half_disable),
    speakFull("Speak", r13, r14, r21, R$drawable.ic_speak_full_disable),
    alarm("Alarm", R$drawable.ic_alarm, R$drawable.ic_live_alarm, r21, R$drawable.ic_alarm_disable),
    light("Light", R$drawable.ic_light, R$drawable.ic_live_light, r2, R$drawable.ic_light_disable),
    sdcard("SD Card", R$drawable.ic_sdcard, 0, 0, R$drawable.ic_sdcard_disable),
    multiView("Multi View", R$drawable.ic_mulit_view, 0, 0, R$drawable.ic_mulit_view_disable),
    album("Album", R$drawable.ic_album, 0, 0, R$drawable.ic_album_disable),
    tracking("Human Tracking", R$drawable.ic_tracking, R$drawable.ic_live_tracking, r15, R$drawable.ic_tracking_disable),
    onoff("On/Off", R$drawable.ic_onoff_n, R$drawable.ic_onoff_s, r15, R$drawable.ic_onoff_disable),
    focus("Focus", R$drawable.ic_focus_n, R$drawable.ic_focus_s, r15, R$drawable.ic_focus_d),
    path("3D Motion", R$drawable.ic_path_n, R$drawable.ic_path_s, r15, R$drawable.ic_path_disable),
    ai("AI Protection", r16, 0, r15, r16);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    int disabledColorId;
    int disabledResId;
    boolean isWorking;
    String name;
    int normalColorId;
    int resId;
    boolean stateEnabled;
    int workingColorId;
    int workingResId;

    private OpItem(String name2, int resId2, int workingResId2, int workingColorId2, int disabledResId2) {
        this.isWorking = false;
        this.stateEnabled = false;
        this.name = name2;
        this.resId = resId2;
        this.workingResId = workingResId2 == 0 ? resId2 : workingResId2;
        int i = R$color.primary_color;
        this.normalColorId = i;
        this.workingColorId = workingColorId2 != 0 ? workingColorId2 : i;
        this.disabledColorId = R$color.grid_disable_color;
        this.disabledResId = disabledResId2;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void setWorking(boolean working) {
        this.isWorking = working;
    }

    public boolean isStateEnabled() {
        return this.stateEnabled;
    }

    public void setStateEnabled(boolean stateEnabled2) {
        this.stateEnabled = stateEnabled2;
    }

    public String getDescName() {
        return this.name;
    }

    public void setDescName(String name2) {
        this.name = name2;
    }

    public int getResId() {
        return this.resId;
    }

    public int getWorkingResId() {
        return this.workingResId;
    }

    public int getNormalColor() {
        return this.normalColorId;
    }

    public void setNormalColor(int normalColorId2) {
        this.normalColorId = normalColorId2;
    }

    public int getWorkingColor() {
        return this.workingColorId;
    }

    public void setWorkingColor(int workingColor) {
        this.workingColorId = workingColor;
    }

    public int getDisabledColorId() {
        return this.disabledColorId;
    }

    public void setDisabledColorId(int disabledColorId2) {
        this.disabledColorId = disabledColorId2;
    }

    public int getDisabledResId() {
        return this.disabledResId;
    }

    public void setDisabledResId(int disabledResId2) {
        this.disabledResId = disabledResId2;
    }
}
