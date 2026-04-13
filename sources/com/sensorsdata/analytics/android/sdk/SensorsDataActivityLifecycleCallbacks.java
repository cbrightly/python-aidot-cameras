package com.sensorsdata.analytics.android.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

@TargetApi(14)
public class SensorsDataActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private final Set<SAActivityLifecycleCallbacks> mActivityCallbacks = new HashSet();

    public interface SAActivityLifecycleCallbacks extends Application.ActivityLifecycleCallbacks {
        void onNewIntent(Intent intent);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivityCreated(activity, bundle);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivityStarted(activity);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivityResumed(activity);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivityPaused(activity);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivityStopped(activity);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivitySaveInstanceState(activity, bundle);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        for (SAActivityLifecycleCallbacks activityLifecycleCallbacks : this.mActivityCallbacks) {
            try {
                activityLifecycleCallbacks.onActivityDestroyed(activity);
            } catch (Exception exception) {
                SALog.printStackTrace(exception);
            }
        }
    }

    public void addActivityLifecycleCallbacks(SAActivityLifecycleCallbacks callbacks) {
        this.mActivityCallbacks.add(callbacks);
    }
}
