package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class FilterBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String areaId;
    private String desc;
    private String deviceId;
    private String eventCode;
    private boolean select;
    private String type;

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId2) {
        this.areaId = areaId2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(String eventCode2) {
        this.eventCode = eventCode2;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc2) {
        this.desc = desc2;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setSelect(boolean select2) {
        this.select = select2;
    }
}
