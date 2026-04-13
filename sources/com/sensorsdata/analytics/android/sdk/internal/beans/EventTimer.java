package com.sensorsdata.analytics.android.sdk.internal.beans;

import android.os.SystemClock;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventTimer {
    private long endTime;
    private long eventAccumulatedDuration;
    private boolean isPaused = false;
    private long startTime;
    private final TimeUnit timeUnit;

    public EventTimer(TimeUnit timeUnit2, long startTime2) {
        this.startTime = startTime2;
        this.timeUnit = timeUnit2;
        this.eventAccumulatedDuration = 0;
        this.endTime = -1;
    }

    public String duration() {
        float durationFloat;
        if (this.isPaused) {
            this.endTime = this.startTime;
        } else {
            long j = this.endTime;
            if (j < 0) {
                j = SystemClock.elapsedRealtime();
            }
            this.endTime = j;
        }
        long duration = (this.endTime - this.startTime) + this.eventAccumulatedDuration;
        if (duration < 0 || duration > CostTimeUtil.DAY) {
            return String.valueOf(0);
        }
        try {
            TimeUnit timeUnit2 = this.timeUnit;
            if (timeUnit2 == TimeUnit.MILLISECONDS) {
                durationFloat = (float) duration;
            } else if (timeUnit2 == TimeUnit.SECONDS) {
                durationFloat = ((float) duration) / 1000.0f;
            } else if (timeUnit2 == TimeUnit.MINUTES) {
                durationFloat = (((float) duration) / 1000.0f) / 60.0f;
            } else if (timeUnit2 == TimeUnit.HOURS) {
                durationFloat = ((((float) duration) / 1000.0f) / 60.0f) / 60.0f;
            } else {
                durationFloat = (float) duration;
            }
            if (durationFloat < 0.0f) {
                return String.valueOf(0);
            }
            return String.format(Locale.CHINA, "%.3f", new Object[]{Float.valueOf(durationFloat)});
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return String.valueOf(0);
        }
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime2) {
        this.startTime = startTime2;
    }

    public void setEndTime(long endTime2) {
        this.endTime = endTime2;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public long getEventAccumulatedDuration() {
        return this.eventAccumulatedDuration;
    }

    public void setEventAccumulatedDuration(long eventAccumulatedDuration2) {
        this.eventAccumulatedDuration = eventAccumulatedDuration2;
    }

    public void setTimerState(boolean isPaused2, long elapsedRealtime) {
        this.isPaused = isPaused2;
        if (isPaused2) {
            this.eventAccumulatedDuration = (this.eventAccumulatedDuration + elapsedRealtime) - this.startTime;
        }
        this.startTime = elapsedRealtime;
    }

    public boolean isPaused() {
        return this.isPaused;
    }
}
