package com.sensorsdata.analytics.android.sdk.listener;

import org.json.JSONObject;

public interface SAEventListener {
    void identify();

    void login();

    void logout();

    void resetAnonymousId();

    void trackEvent(JSONObject jSONObject);
}
