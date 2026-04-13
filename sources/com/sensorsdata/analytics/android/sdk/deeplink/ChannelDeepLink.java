package com.sensorsdata.analytics.android.sdk.deeplink;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.deeplink.DeepLinkManager;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ChannelDeepLink extends AbsDeepLink {
    ChannelDeepLink(Intent intent) {
        super(intent);
    }

    public void parseDeepLink(Intent intent) {
        Set<String> parameterNames;
        if (intent != null && intent.getData() != null) {
            Uri uri = intent.getData();
            if (uri.isOpaque()) {
                SALog.d("ChannelDeepLink", uri.toString() + " isOpaque");
            } else if (Build.VERSION.SDK_INT >= 11 && (parameterNames = uri.getQueryParameterNames()) != null && parameterNames.size() > 0) {
                Map<String, String> uriParams = new HashMap<>();
                for (String name : parameterNames) {
                    String value = uri.getQueryParameter(name);
                    uriParams.put(name, TextUtils.isEmpty(value) ? "" : value);
                }
                ChannelUtils.parseParams(uriParams);
                DeepLinkManager.OnDeepLinkParseFinishCallback onDeepLinkParseFinishCallback = this.mCallBack;
                if (onDeepLinkParseFinishCallback != null) {
                    onDeepLinkParseFinishCallback.onFinish(DeepLinkManager.DeepLinkType.CHANNEL, (String) null, true, 0);
                }
            }
        }
    }

    public void mergeDeepLinkProperty(JSONObject properties) {
        try {
            properties.put("$deeplink_url", (Object) getDeepLinkUrl());
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
        SensorsDataUtils.mergeJSONObject(ChannelUtils.getUtmProperties(), properties);
    }
}
