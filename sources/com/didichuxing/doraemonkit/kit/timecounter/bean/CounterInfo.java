package com.didichuxing.doraemonkit.kit.timecounter.bean;

public class CounterInfo {
    public static final int ACTIVITY_COST_FAST = 500;
    public static final int ACTIVITY_COST_SLOW = 1000;
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_APP = 0;
    public long launchCost;
    public long otherCost;
    public long pauseCost;
    public long renderCost;
    public boolean show;
    public long time;
    public String title;
    public long totalCost;
    public int type;

    public String toString() {
        return "CounterInfo{time=" + this.time + ", type=" + this.type + ", title='" + this.title + '\'' + ", totalCost=" + this.totalCost + ", pauseCost=" + this.pauseCost + ", launchCost=" + this.launchCost + ", renderCost=" + this.renderCost + ", otherCost=" + this.otherCost + ", show=" + this.show + '}';
    }
}
