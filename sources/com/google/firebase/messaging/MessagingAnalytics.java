package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.TransportFactory;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.reporting.MessagingClientEvent;
import com.google.firebase.messaging.reporting.MessagingClientEventExtension;
import java.util.concurrent.ExecutionException;
import meshsdk.model.GroupInfo;

public class MessagingAnalytics {
    private static final String DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF = "export_to_big_query";
    private static final String FCM_PREFERENCES = "com.google.firebase.messaging";
    private static final String MANIFEST_DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_ENABLED = "delivery_metrics_exported_to_big_query_enabled";
    private static final String REENGAGEMENT_MEDIUM = "notification";
    private static final String REENGAGEMENT_SOURCE = "Firebase";

    public static void logNotificationReceived(Intent intent) {
        if (shouldUploadScionMetrics(intent)) {
            logToScion(Constants.ScionAnalytics.EVENT_NOTIFICATION_RECEIVE, intent.getExtras());
        }
        if (shouldUploadFirelogAnalytics(intent)) {
            logToFirelog(MessagingClientEvent.Event.MESSAGE_DELIVERED, intent, FirebaseMessaging.getTransportFactory());
        }
    }

    public static void logNotificationOpen(Bundle extras) {
        setUserPropertyIfRequired(extras);
        logToScion(Constants.ScionAnalytics.EVENT_NOTIFICATION_OPEN, extras);
    }

    public static void logNotificationDismiss(Intent intent) {
        logToScion(Constants.ScionAnalytics.EVENT_NOTIFICATION_DISMISS, intent.getExtras());
    }

    public static void logNotificationForeground(Intent intent) {
        logToScion(Constants.ScionAnalytics.EVENT_NOTIFICATION_FOREGROUND, intent.getExtras());
    }

    public static boolean shouldUploadScionMetrics(Intent intent) {
        if (intent == null || isDirectBootMessage(intent)) {
            return false;
        }
        return shouldUploadScionMetrics(intent.getExtras());
    }

    public static boolean shouldUploadScionMetrics(Bundle extras) {
        if (extras == null) {
            return false;
        }
        return "1".equals(extras.getString(Constants.AnalyticsKeys.ENABLED));
    }

    public static boolean shouldUploadFirelogAnalytics(Intent intent) {
        if (intent == null || isDirectBootMessage(intent)) {
            return false;
        }
        return deliveryMetricsExportToBigQueryEnabled();
    }

    private static boolean isDirectBootMessage(Intent intent) {
        return FirebaseMessagingService.ACTION_DIRECT_BOOT_REMOTE_INTENT.equals(intent.getAction());
    }

    static boolean deliveryMetricsExportToBigQueryEnabled() {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        try {
            FirebaseApp.getInstance();
            Context context = FirebaseApp.getInstance().getApplicationContext();
            SharedPreferences preferences = context.getSharedPreferences("com.google.firebase.messaging", 0);
            if (preferences.contains(DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF)) {
                return preferences.getBoolean(DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF, false);
            }
            try {
                PackageManager packageManager = context.getPackageManager();
                if (!(packageManager == null || (applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128)) == null || (bundle = applicationInfo.metaData) == null || !bundle.containsKey(MANIFEST_DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_ENABLED))) {
                    return applicationInfo.metaData.getBoolean(MANIFEST_DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_ENABLED, false);
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
            return false;
        } catch (IllegalStateException e2) {
            Log.i(Constants.TAG, "FirebaseApp has not being initialized. Device might be in direct boot mode. Skip exporting delivery metrics to Big Query");
            return false;
        }
    }

    private static void setUserPropertyIfRequired(Bundle extras) {
        if (extras != null) {
            if ("1".equals(extras.getString(Constants.AnalyticsKeys.TRACK_CONVERSIONS))) {
                AnalyticsConnector analytics = (AnalyticsConnector) FirebaseApp.getInstance().get(AnalyticsConnector.class);
                if (Log.isLoggable(Constants.TAG, 3)) {
                    Log.d(Constants.TAG, "Received event with track-conversion=true. Setting user property and reengagement event");
                }
                if (analytics != null) {
                    String composerId = extras.getString(Constants.AnalyticsKeys.COMPOSER_ID);
                    analytics.setUserProperty("fcm", Constants.ScionAnalytics.USER_PROPERTY_FIREBASE_LAST_NOTIFICATION, composerId);
                    Bundle params = new Bundle();
                    params.putString("source", REENGAGEMENT_SOURCE);
                    params.putString("medium", REENGAGEMENT_MEDIUM);
                    params.putString("campaign", composerId);
                    analytics.logEvent("fcm", Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, params);
                    return;
                }
                Log.w(Constants.TAG, "Unable to set user property for conversion tracking:  analytics library is missing");
            } else if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Received event with track-conversion=false. Do not set user property");
            }
        }
    }

    @VisibleForTesting
    static void logToScion(String event, Bundle extras) {
        try {
            FirebaseApp.getInstance();
            if (extras == null) {
                extras = new Bundle();
            }
            Bundle scionPayload = new Bundle();
            String composerId = getComposerId(extras);
            if (composerId != null) {
                scionPayload.putString("_nmid", composerId);
            }
            String composerLabel = getComposerLabel(extras);
            if (composerLabel != null) {
                scionPayload.putString(Constants.ScionAnalytics.PARAM_MESSAGE_NAME, composerLabel);
            }
            String messageLabel = getMessageLabel(extras);
            if (!TextUtils.isEmpty(messageLabel)) {
                scionPayload.putString(Constants.ScionAnalytics.PARAM_LABEL, messageLabel);
            }
            String messageChannel = getMessageChannel(extras);
            if (!TextUtils.isEmpty(messageChannel)) {
                scionPayload.putString(Constants.ScionAnalytics.PARAM_MESSAGE_CHANNEL, messageChannel);
            }
            String topic = getTopic(extras);
            if (topic != null) {
                scionPayload.putString(Constants.ScionAnalytics.PARAM_TOPIC, topic);
            }
            String messageTime = getMessageTime(extras);
            if (messageTime != null) {
                try {
                    scionPayload.putInt(Constants.ScionAnalytics.PARAM_MESSAGE_TIME, Integer.parseInt(messageTime));
                } catch (NumberFormatException e) {
                    Log.w(Constants.TAG, "Error while parsing timestamp in GCM event", e);
                }
            }
            String useDeviceTime = getUseDeviceTime(extras);
            if (useDeviceTime != null) {
                try {
                    scionPayload.putInt(Constants.ScionAnalytics.PARAM_MESSAGE_DEVICE_TIME, Integer.parseInt(useDeviceTime));
                } catch (NumberFormatException e2) {
                    Log.w(Constants.TAG, "Error while parsing use_device_time in GCM event", e2);
                }
            }
            String messageType = getMessageTypeForScion(extras);
            if (Constants.ScionAnalytics.EVENT_NOTIFICATION_RECEIVE.equals(event) || Constants.ScionAnalytics.EVENT_NOTIFICATION_FOREGROUND.equals(event)) {
                scionPayload.putString(Constants.ScionAnalytics.PARAM_MESSAGE_TYPE, messageType);
            }
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Logging to scion event=" + event + " scionPayload=" + scionPayload);
            }
            AnalyticsConnector analytics = (AnalyticsConnector) FirebaseApp.getInstance().get(AnalyticsConnector.class);
            if (analytics != null) {
                analytics.logEvent("fcm", event, scionPayload);
            } else {
                Log.w(Constants.TAG, "Unable to log event: analytics library is missing");
            }
        } catch (IllegalStateException e3) {
            Log.e(Constants.TAG, "Default FirebaseApp has not been initialized. Skip logging event to GA.");
        }
    }

    private static void logToFirelog(MessagingClientEvent.Event event, Intent intent, @Nullable TransportFactory transportFactory) {
        if (transportFactory == null) {
            Log.e(Constants.TAG, "TransportFactory is null. Skip exporting message delivery metrics to Big Query");
            return;
        }
        MessagingClientEvent clientEvent = eventToProto(event, intent);
        if (clientEvent != null) {
            try {
                transportFactory.getTransport(Constants.FirelogAnalytics.FCM_LOG_SOURCE, MessagingClientEventExtension.class, Encoding.of("proto"), a.a).send(Event.ofData(MessagingClientEventExtension.newBuilder().setMessagingClientEvent(clientEvent).build()));
            } catch (RuntimeException e) {
                Log.w(Constants.TAG, "Failed to send big query analytics payload.", e);
            }
        }
    }

    static void setDeliveryMetricsExportToBigQuery(boolean enable) {
        FirebaseApp.getInstance().getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit().putBoolean(DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF, enable).apply();
    }

    @NonNull
    static int getTtl(Bundle extras) {
        Object ttl = extras.get(Constants.MessagePayloadKeys.TTL);
        if (ttl instanceof Integer) {
            return ((Integer) ttl).intValue();
        }
        if (!(ttl instanceof String)) {
            return 0;
        }
        try {
            return Integer.parseInt((String) ttl);
        } catch (NumberFormatException e) {
            Log.w(Constants.TAG, "Invalid TTL: " + ttl);
            return 0;
        }
    }

    @Nullable
    static String getCollapseKey(Bundle extras) {
        return extras.getString(Constants.MessagePayloadKeys.COLLAPSE_KEY);
    }

    @Nullable
    static String getComposerId(Bundle extras) {
        return extras.getString(Constants.AnalyticsKeys.COMPOSER_ID);
    }

    @Nullable
    static String getComposerLabel(Bundle extras) {
        return extras.getString(Constants.AnalyticsKeys.COMPOSER_LABEL);
    }

    @Nullable
    static String getMessageLabel(Bundle extras) {
        return extras.getString(Constants.AnalyticsKeys.MESSAGE_LABEL);
    }

    @Nullable
    static String getMessageChannel(Bundle extras) {
        return extras.getString(Constants.AnalyticsKeys.MESSAGE_CHANNEL);
    }

    @Nullable
    static String getMessageTime(Bundle extras) {
        return extras.getString(Constants.AnalyticsKeys.MESSAGE_TIMESTAMP);
    }

    @Nullable
    static String getMessageId(Bundle extras) {
        String messageId = extras.getString(Constants.MessagePayloadKeys.MSGID);
        if (messageId == null) {
            return extras.getString(Constants.MessagePayloadKeys.MSGID_SERVER);
        }
        return messageId;
    }

    @NonNull
    static String getPackageName() {
        return FirebaseApp.getInstance().getApplicationContext().getPackageName();
    }

    @NonNull
    static String getInstanceId(Bundle extras) {
        String to = extras.getString(Constants.MessagePayloadKeys.TO);
        if (!TextUtils.isEmpty(to)) {
            return to;
        }
        try {
            return (String) Tasks.await(FirebaseInstallations.getInstance(FirebaseApp.getInstance()).getId());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    static String getMessageTypeForScion(Bundle extras) {
        if (extras == null || !NotificationParams.isNotification(extras)) {
            return "data";
        }
        return Constants.ScionAnalytics.MessageType.DISPLAY_NOTIFICATION;
    }

    @NonNull
    static MessagingClientEvent.MessageType getMessageTypeForFirelog(Bundle extras) {
        if (extras == null || !NotificationParams.isNotification(extras)) {
            return MessagingClientEvent.MessageType.DATA_MESSAGE;
        }
        return MessagingClientEvent.MessageType.DISPLAY_NOTIFICATION;
    }

    @Nullable
    static String getTopic(Bundle extras) {
        String from = extras.getString("from");
        if (from == null || !from.startsWith("/topics/")) {
            return null;
        }
        return from;
    }

    @Nullable
    static String getUseDeviceTime(Bundle extras) {
        if (extras.containsKey(Constants.AnalyticsKeys.MESSAGE_USE_DEVICE_TIME)) {
            return extras.getString(Constants.AnalyticsKeys.MESSAGE_USE_DEVICE_TIME);
        }
        return null;
    }

    @NonNull
    static int getPriority(Bundle extras) {
        String priority = extras.getString(Constants.MessagePayloadKeys.DELIVERED_PRIORITY);
        if (priority == null) {
            if ("1".equals(extras.getString(Constants.MessagePayloadKeys.PRIORITY_REDUCED_V19))) {
                return 2;
            }
            priority = extras.getString(Constants.MessagePayloadKeys.PRIORITY_V19);
        }
        return getMessagePriority(priority);
    }

    @NonNull
    private static int getMessagePriority(String priority) {
        if ("high".equals(priority)) {
            return 1;
        }
        if (GroupInfo.TYPE_NORMAL.equals(priority)) {
            return 2;
        }
        return 0;
    }

    static int getMessagePriorityForFirelog(Bundle extras) {
        int priority = getPriority(extras);
        if (priority == 2) {
            return 5;
        }
        if (priority == 1) {
            return 10;
        }
        return 0;
    }

    @Nullable
    static long getProjectNumber(Bundle extras) {
        if (extras.containsKey(Constants.MessagePayloadKeys.SENDER_ID)) {
            try {
                return Long.parseLong(extras.getString(Constants.MessagePayloadKeys.SENDER_ID));
            } catch (NumberFormatException ex) {
                Log.w(Constants.TAG, "error parsing project number", ex);
            }
        }
        FirebaseApp app = FirebaseApp.getInstance();
        String senderId = app.getOptions().getGcmSenderId();
        if (senderId != null) {
            try {
                return Long.parseLong(senderId);
            } catch (NumberFormatException ex2) {
                Log.w(Constants.TAG, "error parsing sender ID", ex2);
            }
        }
        String appId = app.getOptions().getApplicationId();
        if (!appId.startsWith("1:")) {
            try {
                return Long.parseLong(appId);
            } catch (NumberFormatException ex3) {
                Log.w(Constants.TAG, "error parsing app ID", ex3);
                return 0;
            }
        } else {
            String[] parts = appId.split(":");
            if (parts.length < 2) {
                return 0;
            }
            String projectId = parts[1];
            if (projectId.isEmpty()) {
                return 0;
            }
            try {
                return Long.parseLong(projectId);
            } catch (NumberFormatException ex4) {
                Log.w(Constants.TAG, "error parsing app ID", ex4);
                return 0;
            }
        }
    }

    static MessagingClientEvent eventToProto(MessagingClientEvent.Event event, Intent intent) {
        if (intent == null) {
            return null;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            extras = Bundle.EMPTY;
        }
        MessagingClientEvent.Builder builder = MessagingClientEvent.newBuilder().setTtl(getTtl(extras)).setEvent(event).setInstanceId(getInstanceId(extras)).setPackageName(getPackageName()).setSdkPlatform(MessagingClientEvent.SDKPlatform.ANDROID).setMessageType(getMessageTypeForFirelog(extras));
        String messageId = getMessageId(extras);
        if (messageId != null) {
            builder.setMessageId(messageId);
        }
        String topic = getTopic(extras);
        if (topic != null) {
            builder.setTopic(topic);
        }
        String collapseKey = getCollapseKey(extras);
        if (collapseKey != null) {
            builder.setCollapseKey(collapseKey);
        }
        String messageLabel = getMessageLabel(extras);
        if (messageLabel != null) {
            builder.setAnalyticsLabel(messageLabel);
        }
        String composerLabel = getComposerLabel(extras);
        if (composerLabel != null) {
            builder.setComposerLabel(composerLabel);
        }
        long projectNumber = getProjectNumber(extras);
        if (projectNumber > 0) {
            builder.setProjectNumber(projectNumber);
        }
        return builder.build();
    }
}
