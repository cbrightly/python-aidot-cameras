package com.didichuxing.doraemonkit.kit.timecounter.counter;

import com.didichuxing.doraemonkit.kit.timecounter.bean.CounterInfo;

public class AppCounter {
    private long mAttachCountTime;
    private long mAttachTime;
    private CounterInfo mCounterInfo = new CounterInfo();
    private long mStartCountTime;
    private long mStartTime;

    public long getStartCountTime() {
        return this.mStartCountTime;
    }

    public long getAttachCountTime() {
        return this.mAttachCountTime;
    }

    public void start() {
        this.mStartTime = System.currentTimeMillis();
    }

    public void attachStart() {
        this.mAttachTime = System.currentTimeMillis();
    }

    public void attachEnd() {
        this.mAttachCountTime = System.currentTimeMillis() - this.mAttachTime;
    }

    public void end() {
        this.mStartCountTime = System.currentTimeMillis() - this.mStartTime;
        account();
    }

    public void account() {
        CounterInfo counterInfo = this.mCounterInfo;
        counterInfo.title = "App Setup Cost";
        counterInfo.totalCost = this.mAttachCountTime + this.mStartCountTime;
        counterInfo.type = 0;
        counterInfo.time = System.currentTimeMillis();
    }

    public CounterInfo getAppSetupInfo() {
        return this.mCounterInfo;
    }
}
