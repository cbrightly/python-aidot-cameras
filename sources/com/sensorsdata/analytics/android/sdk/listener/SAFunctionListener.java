package com.sensorsdata.analytics.android.sdk.listener;

import org.json.JSONObject;

public interface SAFunctionListener {
    void call(String str, JSONObject jSONObject);
}
