package com.sensorsdata.analytics.android.sdk.visual;

public interface VTrack {
    boolean isServiceRunning();

    void startUpdates();

    void stopUpdates(boolean z);
}
