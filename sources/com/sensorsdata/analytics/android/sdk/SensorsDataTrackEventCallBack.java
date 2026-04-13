package com.sensorsdata.analytics.android.sdk;

import org.json.JSONObject;

public interface SensorsDataTrackEventCallBack {
    boolean onTrackEvent(String str, JSONObject jSONObject);
}
