package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.WorkerThread;

public final class ProxyNotificationPreferences {
    private static final String FCM_PREFERENCES = "com.google.firebase.messaging";

    private ProxyNotificationPreferences() {
    }

    private static SharedPreferences getPreference(Context context) {
        Context appContext = context.getApplicationContext();
        if (appContext == null) {
            appContext = context;
        }
        return appContext.getSharedPreferences("com.google.firebase.messaging", 0);
    }

    @WorkerThread
    static void setProxyNotificationsInitialized(Context context, boolean isInitialized) {
        SharedPreferences.Editor preferencesEditor = getPreference(context).edit();
        preferencesEditor.putBoolean("proxy_notification_initialized", isInitialized);
        preferencesEditor.apply();
    }

    @WorkerThread
    static boolean isProxyNotificationInitialized(Context context) {
        return getPreference(context).getBoolean("proxy_notification_initialized", false);
    }
}
