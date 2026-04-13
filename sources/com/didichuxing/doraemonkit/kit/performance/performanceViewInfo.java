package com.didichuxing.doraemonkit.kit.performance;

public class performanceViewInfo {
    int interval;
    int performanceType;
    String title;

    public performanceViewInfo(int performanceType2, String title2, int interval2) {
        this.performanceType = performanceType2;
        this.title = title2;
        this.interval = interval2;
    }

    public int getPerformanceType() {
        return this.performanceType;
    }

    public String getTitle() {
        return this.title;
    }

    public int getInterval() {
        return this.interval;
    }
}
