package com.sensorsdata.analytics.android.sdk;

import java.util.concurrent.LinkedBlockingQueue;

public class TrackTaskManager {
    private static TrackTaskManager trackTaskManager;
    private boolean mDataCollectEnable = true;
    private final LinkedBlockingQueue<Runnable> mTrackEventTasks = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<Runnable> mTrackEventTasksCache = new LinkedBlockingQueue<>();

    private TrackTaskManager() {
    }

    public static synchronized TrackTaskManager getInstance() {
        TrackTaskManager trackTaskManager2;
        synchronized (TrackTaskManager.class) {
            try {
                if (trackTaskManager == null) {
                    trackTaskManager = new TrackTaskManager();
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
            trackTaskManager2 = trackTaskManager;
        }
        return trackTaskManager2;
    }

    /* access modifiers changed from: package-private */
    public void addTrackEventTask(Runnable trackEvenTask) {
        try {
            if (this.mDataCollectEnable) {
                this.mTrackEventTasks.put(trackEvenTask);
            } else {
                this.mTrackEventTasksCache.put(trackEvenTask);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void transformTaskQueue(Runnable runnable) {
        try {
            if (this.mTrackEventTasks.size() < 50) {
                this.mTrackEventTasks.put(runnable);
            }
        } catch (InterruptedException e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public Runnable takeTrackEventTask() {
        try {
            if (this.mDataCollectEnable) {
                return this.mTrackEventTasks.take();
            }
            return this.mTrackEventTasksCache.take();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public Runnable pollTrackEventTask() {
        try {
            if (this.mDataCollectEnable) {
                return this.mTrackEventTasks.poll();
            }
            return this.mTrackEventTasksCache.poll();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.mTrackEventTasks.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void setDataCollectEnable(boolean isDataCollectEnable) {
        this.mDataCollectEnable = isDataCollectEnable;
        if (isDataCollectEnable) {
            try {
                this.mTrackEventTasksCache.put(new Runnable() {
                    public void run() {
                    }
                });
            } catch (InterruptedException e) {
                SALog.printStackTrace(e);
            }
        } else {
            this.mTrackEventTasks.put(new Runnable() {
                public void run() {
                }
            });
        }
    }
}
