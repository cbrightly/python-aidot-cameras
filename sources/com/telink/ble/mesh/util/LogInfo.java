package com.telink.ble.mesh.util;

import java.io.Serializable;

public class LogInfo implements Serializable {
    public int level;
    public String logMessage;
    public long millis = System.currentTimeMillis();
    public String tag;
    public long threadId = Thread.currentThread().getId();
    public String threadName = Thread.currentThread().getName();

    public LogInfo(String tag2, String logMessage2, int level2) {
        this.tag = tag2;
        this.level = level2;
        this.logMessage = logMessage2;
    }
}
