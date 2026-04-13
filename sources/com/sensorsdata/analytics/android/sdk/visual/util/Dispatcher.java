package com.sensorsdata.analytics.android.sdk.visual.util;

import android.os.Handler;
import android.os.HandlerThread;

public class Dispatcher {
    private static String TAG = Dispatcher.class.getSimpleName();
    private Handler mHandler;

    public static Dispatcher getInstance() {
        return DispatchHolder.INSTANCE;
    }

    private Dispatcher() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public static class DispatchHolder {
        /* access modifiers changed from: private */
        public static final Dispatcher INSTANCE = new Dispatcher();

        private DispatchHolder() {
        }
    }

    public void post(Runnable r) {
        postDelayed(r, 0);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        this.mHandler.removeCallbacks(r);
        this.mHandler.postDelayed(r, delayMillis);
    }

    public void removeCallbacksAndMessages() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
    }
}
