package com.sensorsdata.analytics.android.sdk;

import org.json.JSONObject;

public interface ScreenAutoTracker {
    String getScreenUrl();

    JSONObject getTrackProperties();
}
