package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class SDRecord {
    public static ChangeQuickRedirect changeQuickRedirect = null;
    public static final int itemType_Event = 0;
    public static final int itemType_Title = 1;
    public int groupIndex = 0;
    public String groupTitle = "";
    private boolean isCheck = false;
    public int itemType = 0;
    private long timestamp;

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp2) {
        this.timestamp = timestamp2;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }
}
