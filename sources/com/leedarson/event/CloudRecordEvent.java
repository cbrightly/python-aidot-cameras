package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class CloudRecordEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String path;
    public int status;

    public CloudRecordEvent(String path2, int status2) {
        this.path = path2;
        this.status = status2;
    }

    public String getPath() {
        return this.path;
    }

    public int getStatus() {
        return this.status;
    }
}
