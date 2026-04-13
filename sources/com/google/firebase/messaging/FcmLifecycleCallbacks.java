package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.firebase.messaging.Constants;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class FcmLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private final Set<Intent> seenIntents = Collections.newSetFromMap(new WeakHashMap());

    FcmLifecycleCallbacks() {
    }

    @SuppressLint({"ThreadPoolCreation"})
    public void onActivityCreated(Activity createdActivity, Bundle instanceState) {
        Intent startingIntent = createdActivity.getIntent();
        if (startingIntent != null && this.seenIntents.add(startingIntent)) {
            if (Build.VERSION.SDK_INT <= 25) {
                new Handler(Looper.getMainLooper()).post(new g(this, startingIntent));
            } else {
                lambda$onActivityCreated$0(startingIntent);
            }
        }
    }

    public void onActivityPaused(Activity pausedActivity) {
        if (pausedActivity.isFinishing()) {
            this.seenIntents.remove(pausedActivity.getIntent());
        }
    }

    public void onActivityDestroyed(Activity destroyedActivity) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    /* access modifiers changed from: private */
    /* renamed from: logNotificationOpen */
    public void lambda$onActivityCreated$0(Intent startingIntent) {
        Bundle analyticsData = null;
        try {
            Bundle extras = startingIntent.getExtras();
            if (extras != null) {
                analyticsData = extras.getBundle(Constants.MessageNotificationKeys.ANALYTICS_DATA);
            }
        } catch (RuntimeException e) {
            Log.w(Constants.TAG, "Failed trying to get analytics data from Intent extras.", e);
        }
        if (MessagingAnalytics.shouldUploadScionMetrics(analyticsData)) {
            MessagingAnalytics.logNotificationOpen(analyticsData);
        }
    }
}
