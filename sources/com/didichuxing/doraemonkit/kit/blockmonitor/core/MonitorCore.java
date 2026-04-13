package com.didichuxing.doraemonkit.kit.blockmonitor.core;

import android.os.SystemClock;
import android.util.Printer;
import com.didichuxing.doraemonkit.kit.blockmonitor.bean.BlockInfo;
import java.util.ArrayList;

public class MonitorCore implements Printer {
    private static final int BLOCK_THRESHOLD_MILLIS = 200;
    private static final String TAG = "MonitorCore";
    private boolean mPrintingStarted = false;
    private StackSampler mStackSampler;
    private long mStartThreadTime = 0;
    private long mStartTime = 0;

    public MonitorCore() {
        StackSampler stackSampler = new StackSampler();
        this.mStackSampler = stackSampler;
        stackSampler.init();
    }

    public void println(String x) {
        if (!this.mPrintingStarted) {
            this.mStartTime = System.currentTimeMillis();
            this.mStartThreadTime = SystemClock.currentThreadTimeMillis();
            this.mPrintingStarted = true;
            this.mStackSampler.startDump();
            return;
        }
        long endTime = System.currentTimeMillis();
        long endThreadTime = SystemClock.currentThreadTimeMillis();
        this.mPrintingStarted = false;
        if (isBlock(endTime)) {
            ArrayList<String> entries = this.mStackSampler.getThreadStackEntries(this.mStartTime, endTime);
            if (entries.size() > 0) {
                BlockMonitorManager.getInstance().notifyBlockEvent(BlockInfo.newInstance().setMainThreadTimeCost(this.mStartTime, endTime, this.mStartThreadTime, endThreadTime).setThreadStackEntries(entries).flushString());
            }
        }
        this.mStackSampler.stopDump();
    }

    private boolean isBlock(long endTime) {
        return endTime - this.mStartTime > 200;
    }

    public void shutDown() {
        this.mStackSampler.shutDown();
    }
}
