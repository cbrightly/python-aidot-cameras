package com.sensorsdata.analytics.android.sdk.deeplink;

import android.content.Intent;
import com.sensorsdata.analytics.android.sdk.deeplink.DeepLinkManager;
import org.json.JSONObject;

public interface DeepLinkProcessor {
    String getDeepLinkUrl();

    void mergeDeepLinkProperty(JSONObject jSONObject);

    void parseDeepLink(Intent intent);

    void setDeepLinkParseFinishCallback(DeepLinkManager.OnDeepLinkParseFinishCallback onDeepLinkParseFinishCallback);

    void setDeepLinkUrl(String str);
}
