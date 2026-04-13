package com.lzf.easyfloat.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import org.jetbrains.annotations.Nullable;

/* compiled from: LifecycleUtils.kt */
public final class LifecycleUtils$setLifecycleCallbacks$1 implements Application.ActivityLifecycleCallbacks {
    LifecycleUtils$setLifecycleCallbacks$1() {
    }

    public void onActivityCreated(@Nullable Activity activity, @Nullable Bundle savedInstanceState) {
    }

    public void onActivityStarted(@Nullable Activity activity) {
        if (activity != null) {
            Activity activity2 = activity;
            LifecycleUtils lifecycleUtils = LifecycleUtils.INSTANCE;
            LifecycleUtils.activityCount = LifecycleUtils.activityCount + 1;
        }
    }

    public void onActivityResumed(@Nullable Activity activity) {
        if (activity != null) {
            Activity it = activity;
            WeakReference access$getMTopActivity$p = LifecycleUtils.mTopActivity;
            if (access$getMTopActivity$p != null) {
                access$getMTopActivity$p.clear();
            }
            LifecycleUtils lifecycleUtils = LifecycleUtils.INSTANCE;
            LifecycleUtils.mTopActivity = new WeakReference(it);
            lifecycleUtils.checkShow(it);
        }
    }

    public void onActivityPaused(@Nullable Activity activity) {
    }

    public void onActivityStopped(@Nullable Activity activity) {
        if (activity != null) {
            LifecycleUtils lifecycleUtils = LifecycleUtils.INSTANCE;
            LifecycleUtils.activityCount = LifecycleUtils.activityCount - 1;
            lifecycleUtils.checkHide(activity);
        }
    }

    public void onActivityDestroyed(@Nullable Activity activity) {
    }

    public void onActivitySaveInstanceState(@Nullable Activity activity, @Nullable Bundle outState) {
    }
}
