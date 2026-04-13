package com.sensorsdata.analytics.android.sdk.deeplink;

import android.content.Intent;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.ServerUrl;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.deeplink.DeepLinkManager;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.network.HttpMethod;
import com.sensorsdata.analytics.android.sdk.network.RequestHelper;
import com.sensorsdata.analytics.android.sdk.util.JSONUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorsDataDeepLink extends AbsDeepLink {
    /* access modifiers changed from: private */
    public String errorMsg;
    /* access modifiers changed from: private */
    public String pageParams;
    private String project;
    private String serverUrl;
    /* access modifiers changed from: private */
    public boolean success;

    public SensorsDataDeepLink(Intent intent, String serverUrl2) {
        super(intent);
        this.serverUrl = serverUrl2;
        this.project = new ServerUrl(serverUrl2).getProject();
    }

    public void parseDeepLink(Intent intent) {
        if (intent != null && intent.getData() != null) {
            String key = intent.getData().getLastPathSegment();
            if (!TextUtils.isEmpty(key)) {
                final long requestDeepLinkStartTime = System.currentTimeMillis();
                Map<String, String> params = new HashMap<>();
                params.put(CacheEntity.KEY, key);
                params.put("system_type", "ANDROID");
                params.put("project", this.project);
                new RequestHelper.Builder(HttpMethod.GET, getRequestUrl()).params(params).callback(new HttpCallback.JsonCallback() {
                    public void onFailure(int code, String errorMessage) {
                        String unused = SensorsDataDeepLink.this.errorMsg = errorMessage;
                        boolean unused2 = SensorsDataDeepLink.this.success = false;
                    }

                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            boolean unused = SensorsDataDeepLink.this.success = true;
                            ChannelUtils.parseParams(JSONUtils.json2Map(response.optJSONObject("channel_params")));
                            String unused2 = SensorsDataDeepLink.this.pageParams = response.optString("page_params");
                            String unused3 = SensorsDataDeepLink.this.errorMsg = response.optString("errorMsg");
                            if (!TextUtils.isEmpty(SensorsDataDeepLink.this.errorMsg)) {
                                boolean unused4 = SensorsDataDeepLink.this.success = false;
                                return;
                            }
                            return;
                        }
                        boolean unused5 = SensorsDataDeepLink.this.success = false;
                    }

                    public void onAfter() {
                        long duration = System.currentTimeMillis() - requestDeepLinkStartTime;
                        JSONObject properties = new JSONObject();
                        try {
                            if (!TextUtils.isEmpty(SensorsDataDeepLink.this.pageParams)) {
                                properties.put("$deeplink_options", (Object) SensorsDataDeepLink.this.pageParams);
                            }
                            if (!TextUtils.isEmpty(SensorsDataDeepLink.this.errorMsg)) {
                                properties.put("$deeplink_match_fail_reason", (Object) SensorsDataDeepLink.this.errorMsg);
                            }
                            properties.put("$deeplink_url", (Object) SensorsDataDeepLink.this.getDeepLinkUrl());
                            properties.put("$event_duration", (Object) String.format(Locale.CHINA, "%.3f", new Object[]{Float.valueOf(((float) duration) / 1000.0f)}));
                        } catch (JSONException e) {
                            SALog.printStackTrace(e);
                        }
                        SensorsDataUtils.mergeJSONObject(ChannelUtils.getUtmProperties(), properties);
                        SensorsDataDeepLink sensorsDataDeepLink = SensorsDataDeepLink.this;
                        DeepLinkManager.OnDeepLinkParseFinishCallback onDeepLinkParseFinishCallback = sensorsDataDeepLink.mCallBack;
                        if (onDeepLinkParseFinishCallback != null) {
                            onDeepLinkParseFinishCallback.onFinish(DeepLinkManager.DeepLinkType.SENSORSDATA, sensorsDataDeepLink.pageParams, SensorsDataDeepLink.this.success, duration);
                        }
                        SensorsDataAPI.sharedInstance().trackInternal("$AppDeeplinkMatchedResult", properties);
                    }
                }).execute();
            }
        }
    }

    public void mergeDeepLinkProperty(JSONObject properties) {
        try {
            properties.put("$deeplink_url", (Object) getDeepLinkUrl());
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public String getRequestUrl() {
        int pathPrefix;
        if (TextUtils.isEmpty(this.serverUrl) || (pathPrefix = this.serverUrl.lastIndexOf("/")) == -1) {
            return "";
        }
        return this.serverUrl.substring(0, pathPrefix) + "/sdk/deeplink/param";
    }
}
