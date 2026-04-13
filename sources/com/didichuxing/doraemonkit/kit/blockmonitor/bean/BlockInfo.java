package com.didichuxing.doraemonkit.kit.blockmonitor.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class BlockInfo {
    private static final String KEY_STACK = "stack";
    private static final String KEY_THREAD_TIME_COST = "thread-time";
    private static final String KEY_TIME_COST = "time";
    private static final String KEY_TIME_COST_END = "time-end";
    private static final String KEY_TIME_COST_START = "time-start";
    private static final String KV = " = ";
    public static final String NEW_INSTANCE_METHOD = "newInstance: ";
    public static final String SEPARATOR = "\r\n";
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINESE);
    public String concernStackString;
    private StringBuilder stackSb = new StringBuilder();
    public ArrayList<String> threadStackEntries = new ArrayList<>();
    private long threadTimeCost;
    public long time;
    public long timeCost;
    private String timeEnd;
    private StringBuilder timeSb = new StringBuilder();
    public String timeStart;

    public static BlockInfo newInstance() {
        return new BlockInfo();
    }

    public BlockInfo flushString() {
        StringBuilder sb = this.timeSb;
        sb.append(KEY_TIME_COST);
        sb.append(KV);
        sb.append(this.timeCost);
        sb.append("\r\n");
        StringBuilder sb2 = this.timeSb;
        sb2.append(KEY_THREAD_TIME_COST);
        sb2.append(KV);
        sb2.append(this.threadTimeCost);
        sb2.append("\r\n");
        StringBuilder sb3 = this.timeSb;
        sb3.append(KEY_TIME_COST_START);
        sb3.append(KV);
        sb3.append(this.timeStart);
        sb3.append("\r\n");
        StringBuilder sb4 = this.timeSb;
        sb4.append(KEY_TIME_COST_END);
        sb4.append(KV);
        sb4.append(this.timeEnd);
        sb4.append("\r\n");
        ArrayList<String> arrayList = this.threadStackEntries;
        if (arrayList != null && !arrayList.isEmpty()) {
            StringBuilder temp = new StringBuilder();
            Iterator<String> it = this.threadStackEntries.iterator();
            while (it.hasNext()) {
                temp.append(it.next());
                temp.append("\r\n");
            }
            StringBuilder sb5 = this.stackSb;
            sb5.append(KEY_STACK);
            sb5.append(KV);
            sb5.append(temp.toString());
            sb5.append("\r\n");
        }
        return this;
    }

    public BlockInfo setThreadStackEntries(ArrayList<String> threadStackEntries2) {
        this.threadStackEntries = threadStackEntries2;
        return this;
    }

    public BlockInfo setMainThreadTimeCost(long realTimeStart, long realTimeEnd, long threadTimeStart, long threadTimeEnd) {
        this.timeCost = realTimeEnd - realTimeStart;
        this.threadTimeCost = threadTimeEnd - threadTimeStart;
        SimpleDateFormat simpleDateFormat = TIME_FORMATTER;
        this.timeStart = simpleDateFormat.format(Long.valueOf(realTimeStart));
        this.timeEnd = simpleDateFormat.format(Long.valueOf(realTimeEnd));
        return this;
    }

    public String toString() {
        return this.timeSb.toString() + "\n" + this.stackSb.toString();
    }
}
