package com.sensorsdata.analytics.android.sdk.deeplink;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.ServerUrl;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.advert.utils.OaidHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.Date;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class DeepLinkManager {
    public static final String IS_ANALYTICS_DEEPLINK = "is_analytics_deeplink";
    private static DeepLinkProcessor mDeepLinkProcessor;

    public enum DeepLinkType {
        CHANNEL,
        SENSORSDATA
    }

    public interface OnDeepLinkParseFinishCallback {
        void onFinish(DeepLinkType deepLinkType, String str, boolean z, long j);
    }

    private static boolean isDeepLink(Intent intent) {
        return Build.VERSION.SDK_INT >= 11 && intent != null && "android.intent.action.VIEW".equals(intent.getAction());
    }

    private static boolean isUtmDeepLink(Intent intent) {
        if (!isDeepLink(intent) || intent.getData() == null) {
            return false;
        }
        Uri uri = intent.getData();
        if (uri.isOpaque()) {
            SALog.d("ChannelDeepLink", uri.toString() + " isOpaque");
            return false;
        }
        Set<String> parameterNames = uri.getQueryParameterNames();
        if (parameterNames == null || parameterNames.size() <= 0) {
            return false;
        }
        return ChannelUtils.hasLinkUtmProperties(parameterNames);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0014, code lost:
        r0 = r5.getData();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isSensorsDataDeepLink(android.content.Intent r5, java.lang.String r6) {
        /*
            boolean r0 = isDeepLink(r5)
            r1 = 0
            if (r0 == 0) goto L_0x004f
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L_0x004f
            android.net.Uri r0 = r5.getData()
            if (r0 != 0) goto L_0x0014
            goto L_0x004f
        L_0x0014:
            android.net.Uri r0 = r5.getData()
            java.util.List r2 = r0.getPathSegments()
            if (r2 == 0) goto L_0x004e
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x004e
            java.lang.Object r3 = r2.get(r1)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "sd"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x004e
            java.lang.String r3 = r0.getHost()
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x004d
            boolean r4 = r3.equals(r6)
            if (r4 != 0) goto L_0x004c
            java.lang.String r4 = "sensorsdata"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x004d
        L_0x004c:
            r1 = 1
        L_0x004d:
            return r1
        L_0x004e:
            return r1
        L_0x004f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.deeplink.DeepLinkManager.isSensorsDataDeepLink(android.content.Intent, java.lang.String):boolean");
    }

    private static DeepLinkProcessor createDeepLink(Intent intent, String serverUrl) {
        if (intent == null) {
            return null;
        }
        if (isSensorsDataDeepLink(intent, new ServerUrl(serverUrl).getHost())) {
            return new SensorsDataDeepLink(intent, serverUrl);
        }
        if (isUtmDeepLink(intent)) {
            return new ChannelDeepLink(intent);
        }
        return null;
    }

    private static void trackDeepLinkLaunchEvent(final Context context, DeepLinkProcessor deepLink) {
        final JSONObject properties = new JSONObject();
        final SensorsDataAPI sensorsDataAPI = SensorsDataAPI.sharedInstance();
        final boolean isDeepLinkInstallSource = (deepLink instanceof SensorsDataDeepLink) && sensorsDataAPI.isDeepLinkInstallSource();
        try {
            properties.put("$deeplink_url", (Object) deepLink.getDeepLinkUrl());
            properties.put("$time", (Object) new Date(System.currentTimeMillis()));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
        SensorsDataUtils.mergeJSONObject(ChannelUtils.getLatestUtmProperties(), properties);
        SensorsDataUtils.mergeJSONObject(ChannelUtils.getUtmProperties(), properties);
        sensorsDataAPI.transformTaskQueue(new Runnable() {
            public void run() {
                if (isDeepLinkInstallSource) {
                    try {
                        JSONObject jSONObject = properties;
                        Context context = context;
                        jSONObject.put("$ios_install_source", (Object) ChannelUtils.getDeviceInfo(context, SensorsDataUtils.getAndroidID(context), OaidHelper.getOAID(context)));
                    } catch (JSONException e) {
                        SALog.printStackTrace(e);
                    }
                }
                sensorsDataAPI.trackInternal("$AppDeeplinkLaunch", properties);
            }
        });
    }

    public static boolean parseDeepLink(final Activity activity, final boolean isSaveDeepLinkInfo, final SensorsDataDeepLinkCallback callback) {
        try {
            Intent intent = activity.getIntent();
            DeepLinkProcessor createDeepLink = createDeepLink(intent, SensorsDataAPI.sharedInstance().getServerUrl());
            mDeepLinkProcessor = createDeepLink;
            if (createDeepLink == null) {
                return false;
            }
            ChannelUtils.clearUtm(activity.getApplicationContext());
            mDeepLinkProcessor.setDeepLinkParseFinishCallback(new OnDeepLinkParseFinishCallback() {
                public void onFinish(DeepLinkType deepLinkStatus, String params, boolean success, long duration) {
                    if (isSaveDeepLinkInfo) {
                        ChannelUtils.saveDeepLinkInfo(activity.getApplicationContext());
                    }
                    SensorsDataDeepLinkCallback sensorsDataDeepLinkCallback = callback;
                    if (sensorsDataDeepLinkCallback != null && deepLinkStatus == DeepLinkType.SENSORSDATA) {
                        sensorsDataDeepLinkCallback.onReceive(params, success, duration);
                    }
                }
            });
            mDeepLinkProcessor.parseDeepLink(intent);
            trackDeepLinkLaunchEvent(activity.getApplicationContext(), mDeepLinkProcessor);
            return true;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return false;
        }
    }

    public static void mergeDeepLinkProperty(JSONObject properties) {
        try {
            DeepLinkProcessor deepLinkProcessor = mDeepLinkProcessor;
            if (deepLinkProcessor != null) {
                deepLinkProcessor.mergeDeepLinkProperty(properties);
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public static void resetDeepLinkProcessor() {
        mDeepLinkProcessor = null;
    }
}
