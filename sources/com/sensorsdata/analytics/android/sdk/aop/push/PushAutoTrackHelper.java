package com.sensorsdata.analytics.android.sdk.aop.push;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.util.SystemUtil;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPIEmptyImplementation;
import com.sensorsdata.analytics.android.sdk.util.ReflectUtil;
import java.util.Date;
import meshsdk.cache.CacheHandler;
import org.json.JSONObject;

public class PushAutoTrackHelper {
    private static final String TAG = "SA.PushAutoTrackHelper";
    private static long lastPushClickTime = 0;

    public static void trackJPushOpenActivity(Intent intent) {
        if (intent != null && isTrackPushEnabled()) {
            String data = null;
            if (intent.getData() != null) {
                data = intent.getData().toString();
            }
            if (TextUtils.isEmpty(data) && intent.getExtras() != null) {
                data = intent.getExtras().getString("JMessageExtra");
            }
            SALog.i(TAG, "trackJPushOpenActivity is called, Intent data is " + data);
            if (!TextUtils.isEmpty(data)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                } catch (Exception e) {
                    try {
                        SALog.i(TAG, "Failed to construct JSON");
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                        return;
                    }
                }
                if (jsonObject != null) {
                    String title = jsonObject.optString("n_title");
                    String content = jsonObject.optString("n_content");
                    String extras = jsonObject.optString("n_extras");
                    String appPushChannel = PushUtils.getJPushSDKName((byte) jsonObject.optInt("rom_type"));
                    SALog.i(TAG, String.format("trackJPushOpenActivity is called, title is %s, content is %s, extras is %s, appPushChannel is %s", new Object[]{title, content, extras, appPushChannel}));
                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                        if (!TextUtils.isEmpty(appPushChannel)) {
                            trackNotificationOpenedEvent(getSFData(extras), title, content, "JPush", appPushChannel);
                        }
                    }
                }
            }
        }
    }

    public static void trackNotificationOpenedEvent(String sfData, String title, String content, String appPushServiceName, String appPushChannel) {
        trackNotificationOpenedEvent(sfData, title, content, appPushServiceName, appPushChannel, 0);
    }

    private static void trackNotificationOpenedEvent(String sfData, String title, String content, String appPushServiceName, String appPushChannel, long time) {
        try {
            if (isRepeatEvent()) {
                SALog.i(TAG, String.format("$AppPushClick Repeat trigger, title is %s, content is %s, extras is %s, appPushChannel is %s, appPushServiceName is %s", new Object[]{title, content, sfData, appPushChannel, appPushServiceName}));
                return;
            }
            JSONObject eventProperties = new JSONObject();
            eventProperties.put("$app_push_msg_title", (Object) title);
            eventProperties.put("$app_push_msg_content", (Object) content);
            eventProperties.put("$app_push_service_name", (Object) appPushServiceName);
            if (!TextUtils.isEmpty(appPushChannel)) {
                eventProperties.put("$app_push_channel", (Object) appPushChannel.toUpperCase());
            }
            JSONObject sfDataProperties = null;
            try {
                if (!TextUtils.isEmpty(sfData)) {
                    try {
                        SALog.i(TAG, "sfData is " + sfData);
                        sfDataProperties = new JSONObject(sfData);
                    } catch (Exception e) {
                        SALog.i(TAG, "Failed to construct JSON");
                    }
                }
                if (sfDataProperties != null && sfDataProperties.has("sf_plan_id")) {
                    eventProperties.put("$sf_msg_title", (Object) title);
                    eventProperties.put("$sf_msg_content", (Object) content);
                    eventProperties.put("$sf_msg_id", sfDataProperties.opt("sf_msg_id"));
                    eventProperties.put("$sf_plan_id", sfDataProperties.opt("sf_plan_id"));
                    eventProperties.put("$sf_audience_id", sfDataProperties.opt("sf_audience_id"));
                    eventProperties.put("$sf_link_url", sfDataProperties.opt("sf_link_url"));
                    eventProperties.put("$sf_plan_strategy_id", sfDataProperties.opt("sf_plan_strategy_id"));
                    eventProperties.put("$sf_plan_type", sfDataProperties.opt("sf_plan_type"));
                    eventProperties.put("$sf_strategy_unit_id", sfDataProperties.opt("sf_strategy_unit_id"));
                    eventProperties.put("$sf_enter_plan_time", sfDataProperties.opt("sf_enter_plan_time"));
                    eventProperties.put("$sf_channel_id", sfDataProperties.opt("sf_channel_id"));
                    eventProperties.put("$sf_channel_category", sfDataProperties.opt("sf_channel_category"));
                    eventProperties.put("$sf_channel_service_name", sfDataProperties.opt("sf_channel_service_name"));
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
            }
            if (time > 0) {
                try {
                    eventProperties.put("$time", (Object) new Date(time));
                } catch (Exception e3) {
                    SALog.printStackTrace(e3);
                }
            }
            SensorsDataAPI.sharedInstance().track("$AppPushClick", eventProperties);
        } catch (Exception e4) {
            SALog.printStackTrace(e4);
        }
    }

    public static void trackGeTuiNotificationClicked(String title, String content, String sfData, long time) {
        trackNotificationOpenedEvent(sfData, title, content, "GeTui", (String) null, time);
    }

    private static boolean isRepeatEvent() {
        long currentTime = SystemClock.elapsedRealtime();
        SALog.i(TAG, "currentTime: " + currentTime + ",lastPushClickTime: " + lastPushClickTime);
        if (currentTime - lastPushClickTime <= CacheHandler.delayTime) {
            return true;
        }
        lastPushClickTime = currentTime;
        return false;
    }

    public static void trackJPushAppOpenNotification(String extras, String title, String content, String appPushChannel) {
        if (isTrackPushEnabled()) {
            SALog.i(TAG, String.format("trackJPushAppOpenNotification is called, title is %s, content is %s, extras is %s, appPushChannel is %s, appPushServiceName is %s", new Object[]{title, content, extras, appPushChannel, "JPush"}));
            trackNotificationOpenedEvent(getSFData(extras), title, content, "JPush", appPushChannel);
        }
    }

    public static void trackMeizuAppOpenNotification(String extras, String title, String content, String appPushServiceName) {
        JSONObject contentJson;
        if (isTrackPushEnabled()) {
            SALog.i(TAG, String.format("trackMeizuAppOpenNotification is called, title is %s, content is %s, extras is %s, appPushChannel is %s, appPushServiceName is %s", new Object[]{title, content, extras, SystemUtil.PHONE_MEIZU, appPushServiceName}));
            String nExtras = extras;
            JSONObject extrasJson = null;
            try {
                extrasJson = new JSONObject(extras);
            } catch (Exception e) {
                try {
                    SALog.i(TAG, "Failed to construct JSON");
                } catch (Exception e2) {
                    try {
                        SALog.printStackTrace(e2);
                    } catch (Exception e3) {
                        SALog.printStackTrace(e3);
                        return;
                    }
                }
            }
            if (extrasJson != null && extrasJson.has("JMessageExtra")) {
                JSONObject jMessageJson = extrasJson.optJSONObject("JMessageExtra");
                if (!(jMessageJson == null || (contentJson = jMessageJson.optJSONObject("m_content")) == null)) {
                    nExtras = contentJson.optString("n_extras");
                }
                appPushServiceName = "JPush";
            }
            trackNotificationOpenedEvent(getSFData(nExtras), title, content, appPushServiceName, SystemUtil.PHONE_MEIZU);
        }
    }

    public static void onGeTuiNotificationClicked(Object gtNotificationMessage) {
        if (gtNotificationMessage == null) {
            SALog.i(TAG, "gtNotificationMessage is null");
        } else if (isTrackPushEnabled()) {
            try {
                String msgId = (String) ReflectUtil.callMethod(gtNotificationMessage, "getMessageId", new Object[0]);
                String title = (String) ReflectUtil.callMethod(gtNotificationMessage, "getTitle", new Object[0]);
                String content = (String) ReflectUtil.callMethod(gtNotificationMessage, "getContent", new Object[0]);
                if (!TextUtils.isEmpty(msgId) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                    PushProcess.getInstance().trackGTClickDelayed(msgId, title, content);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void onGeTuiReceiveMessageData(Object gtTransmitMessage) {
        if (gtTransmitMessage == null) {
            SALog.i(TAG, "gtNotificationMessage is null");
        } else if (isTrackPushEnabled()) {
            try {
                byte[] bytes = (byte[]) ReflectUtil.callMethod(gtTransmitMessage, "getPayload", new Object[0]);
                String msgId = (String) ReflectUtil.callMethod(gtTransmitMessage, "getMessageId", new Object[0]);
                if (bytes != null && !TextUtils.isEmpty(msgId)) {
                    PushProcess.getInstance().trackReceiveMessageData(new String(bytes), msgId);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void onUMengNotificationClick(Object UMessage) {
        if (UMessage == null) {
            SALog.i(TAG, "UMessage is null");
        } else if (isTrackPushEnabled()) {
            try {
                JSONObject raw = (JSONObject) ReflectUtil.callMethod(UMessage, "getRaw", new Object[0]);
                if (raw == null) {
                    SALog.i(TAG, "onUMengNotificationClick:raw is null");
                    return;
                }
                JSONObject body = raw.optJSONObject("body");
                if (body != null) {
                    String extra = raw.optString("extra");
                    String title = body.optString("title");
                    String content = body.optString("text");
                    trackNotificationOpenedEvent(getSFData(extra), title, content, "UMeng", (String) null);
                    SALog.i(TAG, String.format("onUMengNotificationClick is called, title is %s, content is %s, extras is %s", new Object[]{title, content, extra}));
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        r3 = new org.json.JSONObject(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void onUMengActivityMessage(android.content.Intent r12) {
        /*
            java.lang.String r0 = "body"
            java.lang.String r1 = "SA.PushAutoTrackHelper"
            if (r12 != 0) goto L_0x000c
            java.lang.String r0 = "intent is null"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r1, (java.lang.String) r0)
            return
        L_0x000c:
            boolean r2 = isTrackPushEnabled()
            if (r2 != 0) goto L_0x0013
            return
        L_0x0013:
            java.lang.String r2 = r12.getStringExtra(r0)     // Catch:{ Exception -> 0x0062 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0062 }
            if (r3 != 0) goto L_0x0061
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0062 }
            r3.<init>((java.lang.String) r2)     // Catch:{ Exception -> 0x0062 }
            org.json.JSONObject r0 = r3.optJSONObject(r0)     // Catch:{ Exception -> 0x0062 }
            if (r0 == 0) goto L_0x0061
            java.lang.String r4 = "extra"
            java.lang.String r4 = r3.optString(r4)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r5 = "title"
            java.lang.String r5 = r0.optString(r5)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r6 = "text"
            java.lang.String r6 = r0.optString(r6)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r7 = "message_source"
            java.lang.String r7 = r12.getStringExtra(r7)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r8 = getSFData(r4)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r9 = "UMeng"
            trackNotificationOpenedEvent(r8, r5, r6, r9, r7)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r9 = "onUMengActivityMessage is called, title is %s, content is %s, extras is %s"
            r10 = 3
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x0062 }
            r11 = 0
            r10[r11] = r5     // Catch:{ Exception -> 0x0062 }
            r11 = 1
            r10[r11] = r6     // Catch:{ Exception -> 0x0062 }
            r11 = 2
            r10[r11] = r4     // Catch:{ Exception -> 0x0062 }
            java.lang.String r9 = java.lang.String.format(r9, r10)     // Catch:{ Exception -> 0x0062 }
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r1, (java.lang.String) r9)     // Catch:{ Exception -> 0x0062 }
        L_0x0061:
            goto L_0x0066
        L_0x0062:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x0066:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.onUMengActivityMessage(android.content.Intent):void");
    }

    public static void onNotify(NotificationManager manager, String tag, int id, Notification notification) {
        if (isTrackPushEnabled()) {
            try {
                PushProcess.getInstance().onNotify(tag, id, notification);
                SALog.i(TAG, "onNotify");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void onNotify(NotificationManager manager, int id, Notification notification) {
        if (isTrackPushEnabled()) {
            try {
                onNotify(manager, (String) null, id, notification);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void onNewIntent(Object activity, Intent intent) {
        if (isTrackPushEnabled()) {
            try {
                if (activity instanceof Activity) {
                    PushProcess.getInstance().onNotificationClick((Activity) activity, intent);
                    SALog.i(TAG, "onNewIntent");
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void hookPendingIntentGetActivityBundle(PendingIntent pendingIntent, Context context, int requestCode, Intent intent, int flags, Bundle bundle) {
        hookPendingIntent(intent, pendingIntent);
    }

    public static void hookIntentGetActivity(Context context, int requestCode, Intent intent, int flags) {
        hookIntent(intent);
    }

    public static void hookIntentGetActivityBundle(Context context, int requestCode, Intent intent, int flags, Bundle bundle) {
        hookIntent(intent);
    }

    public static void hookPendingIntentGetActivity(PendingIntent pendingIntent, Context context, int requestCode, Intent intent, int flags) {
        hookPendingIntent(intent, pendingIntent);
    }

    public static void onBroadcastReceiver(BroadcastReceiver receiver, Context context, Intent intent) {
        onBroadcastServiceIntent(intent);
    }

    public static void onServiceStart(Service service, Intent intent, int startId) {
        onBroadcastServiceIntent(intent);
    }

    public static void onServiceStartCommand(Service service, Intent intent, int flags, int startId) {
        onBroadcastServiceIntent(intent);
    }

    public static void hookIntentGetBroadcast(Context context, int requestCode, Intent intent, int flags) {
        hookIntent(intent);
    }

    public static void hookPendingIntentGetBroadcast(PendingIntent pendingIntent, Context context, int requestCode, Intent intent, int flags) {
        hookPendingIntent(intent, pendingIntent);
    }

    public static void hookIntentGetService(Context context, int requestCode, Intent intent, int flags) {
        hookIntent(intent);
    }

    public static void hookPendingIntentGetService(PendingIntent pendingIntent, Context context, int requestCode, Intent intent, int flags) {
        hookPendingIntent(intent, pendingIntent);
    }

    public static void hookIntentGetForegroundService(Context context, int requestCode, Intent intent, int flags) {
        hookIntent(intent);
    }

    public static void hookPendingIntentGetForegroundService(PendingIntent pendingIntent, Context context, int requestCode, Intent intent, int flags) {
        hookPendingIntent(intent, pendingIntent);
    }

    private static void hookPendingIntent(Intent intent, PendingIntent pendingIntent) {
        if (isTrackPushEnabled()) {
            try {
                PushProcess.getInstance().hookPendingIntent(intent, pendingIntent);
                SALog.i(TAG, "hookPendingIntent");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    private static void onBroadcastServiceIntent(Intent intent) {
        if (isTrackPushEnabled()) {
            try {
                PushProcess.getInstance().onNotificationClick((Context) null, intent);
                SALog.i(TAG, "onBroadcastServiceIntent");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    private static void hookIntent(Intent intent) {
        if (isTrackPushEnabled()) {
            try {
                PushProcess.getInstance().hookIntent(intent);
                SALog.i(TAG, "hookIntent");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    private static boolean isTrackPushEnabled() {
        try {
            if (!(SensorsDataAPI.sharedInstance() instanceof SensorsDataAPIEmptyImplementation) && AbstractSensorsDataAPI.getConfigOptions() != null) {
                if (AbstractSensorsDataAPI.getConfigOptions().mEnableTrackPush) {
                    return true;
                }
            }
            SALog.i(TAG, "SDK or push disabled.");
            return false;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    private static String getSFData(String extras) {
        try {
            return new JSONObject(extras).optString("sf_data");
        } catch (Exception e) {
            SALog.i(TAG, "get sf_data failed");
            return null;
        }
    }
}
