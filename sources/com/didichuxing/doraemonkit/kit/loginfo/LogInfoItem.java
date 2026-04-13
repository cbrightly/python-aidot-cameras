package com.didichuxing.doraemonkit.kit.loginfo;

public class LogInfoItem {
    public String date;
    public int level = 2;
    public String meseage;
    public String orginalLog;
    public String packagePriority;
    public boolean showFull = false;
    public String tag;
    public String time;

    public LogInfoItem(String log) {
        this.orginalLog = log;
        if (log.contains("V/")) {
            this.level = 2;
        } else if (log.contains("D/")) {
            this.level = 3;
        } else if (log.contains("I/")) {
            this.level = 4;
        } else if (log.contains("W/")) {
            this.level = 5;
        } else if (log.contains("E/")) {
            this.level = 6;
        } else if (log.contains("A/")) {
            this.level = 7;
        }
        int beginIndex = log.indexOf(": ");
        if (beginIndex == -1) {
            this.meseage = log;
        } else {
            this.meseage = log.substring(beginIndex + 2);
        }
        int beginIndex2 = log.indexOf("/");
        int endIndex = log.indexOf("/", beginIndex2 + 1);
        if (!(beginIndex2 == -1 || endIndex == -1)) {
            this.packagePriority = log.substring(beginIndex2 + 1, endIndex - 3);
        }
        int endIndex2 = log.indexOf(" ");
        if (endIndex2 != -1) {
            this.date = log.substring(0, endIndex2);
        }
        int beginIndex3 = endIndex2;
        int endIndex3 = log.indexOf(" ", beginIndex3 + 1);
        if (endIndex3 != -1 && beginIndex3 != -1) {
            this.time = log.substring(beginIndex3, endIndex3);
        }
    }
}
