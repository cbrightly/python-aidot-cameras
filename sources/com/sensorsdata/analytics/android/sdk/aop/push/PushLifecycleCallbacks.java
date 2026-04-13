package com.sensorsdata.analytics.android.sdk.aop.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sensorsdata.analytics.android.sdk.SensorsDataActivityLifecycleCallbacks;

public class PushLifecycleCallbacks implements SensorsDataActivityLifecycleCallbacks.SAActivityLifecycleCallbacks {
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        PushProcess.getInstance().onNotificationClick(activity, activity.getIntent());
    }

    public void onNewIntent(Intent intent) {
    }

    public void onActivityStarted(Activity activity) {
        PushProcess.getInstance().onNotificationClick(activity, activity.getIntent());
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
