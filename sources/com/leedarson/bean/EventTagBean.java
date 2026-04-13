package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import java.io.Serializable;

public class EventTagBean implements Serializable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String desc;
    private String eventCode;
    private boolean select;

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
