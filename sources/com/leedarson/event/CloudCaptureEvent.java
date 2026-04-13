package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class CloudCaptureEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String path;
    public int saveStatus;

    public CloudCaptureEvent(String path2, int saveStatus2) {
        this.path = path2;
        this.saveStatus = saveStatus2;
    }

    public String getPath() {
        return this.path;
    }

    public int getSaveStatus() {
        return this.saveStatus;
    }
}
