package com.didichuxing.doraemonkit.kit.blockmonitor.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class StackSampler {
    private static final int DEFAULT_MAX_ENTRY_COUNT = 100;
    private static final int DEFAULT_SAMPLE_INTERVAL = 300;
    private static final String SEPARATOR = "\r\n";
    private static final String TAG = "StackSampler";
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINESE);
    private String mFilterCache;
    /* access modifiers changed from: private */
    public Runnable mRunnable = new Runnable() {
        public void run() {
            StackSampler.this.dumpInfo();
            if (StackSampler.this.mRunning.get()) {
                StackSampler.this.mStackHandler.postDelayed(StackSampler.this.mRunnable, 300);
            }
        }
    };
    /* access modifiers changed from: private */
    public AtomicBoolean mRunning = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public Handler mStackHandler;
    /* access modifiers changed from: private */
    public HandlerThread mStackThread;
    private LinkedHashMap<Long, String> sStackMap = new LinkedHashMap<>();

    public void init() {
        if (this.mStackThread == null) {
            AnonymousClass1 r0 = new HandlerThread("BlockMonitor") {
                /* access modifiers changed from: protected */
                public void onLooperPrepared() {
                    Handler unused = StackSampler.this.mStackHandler = new Handler(StackSampler.this.mStackThread.getLooper());
                }
            };
            this.mStackThread = r0;
            r0.start();
        }
    }

    public void startDump() {
        if (this.mStackHandler != null && !this.mRunning.get()) {
            this.mRunning.set(true);
            this.mStackHandler.removeCallbacks(this.mRunnable);
            this.mStackHandler.postDelayed(this.mRunnable, 300);
        }
    }

    public ArrayList<String> getThreadStackEntries(long startTime, long endTime) {
        ArrayList<String> result = new ArrayList<>();
        synchronized (this.sStackMap) {
            for (Long entryTime : this.sStackMap.keySet()) {
                if (startTime < entryTime.longValue() && entryTime.longValue() < endTime) {
                    result.add(TIME_FORMATTER.format(entryTime) + "\r\n" + "\r\n" + this.sStackMap.get(entryTime));
                }
            }
        }
        return result;
    }

    public void stopDump() {
        if (this.mStackHandler != null && this.mRunning.get()) {
            this.mRunning.set(false);
            this.mFilterCache = null;
            this.mStackHandler.removeCallbacks(this.mRunnable);
        }
    }

    public void shutDown() {
        stopDump();
        HandlerThread handlerThread = this.mStackThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }

    /* access modifiers changed from: private */
    public void dumpInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : Looper.getMainLooper().getThread().getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString());
            stringBuilder.append("\r\n");
        }
        synchronized (this.sStackMap) {
            if (this.sStackMap.size() == 100) {
                LinkedHashMap<Long, String> linkedHashMap = this.sStackMap;
                linkedHashMap.remove(linkedHashMap.keySet().iterator().next());
            }
            if (!shouldIgnore(stringBuilder)) {
                this.sStackMap.put(Long.valueOf(System.currentTimeMillis()), stringBuilder.toString());
            }
        }
    }

    private boolean shouldIgnore(StringBuilder builder) {
        if (TextUtils.equals(this.mFilterCache, builder.toString())) {
            return true;
        }
        this.mFilterCache = builder.toString();
        return false;
    }
}
