package com.sensorsdata.analytics.android.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.visual.ViewTreeStatusObservable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

@SuppressLint({"NewApi"})
public class AppStateManager implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "SA.AppStateManager";
    private static volatile AppStateManager mSingleton = null;
    private String mCurrentFragmentName = null;
    private int mCurrentRootWindowsHashCode = -1;
    private WeakReference<Activity> mForeGroundActivity = new WeakReference<>((Object) null);

    private AppStateManager() {
    }

    public static AppStateManager getInstance() {
        if (mSingleton == null) {
            synchronized (AppStateManager.class) {
                if (mSingleton == null) {
                    mSingleton = new AppStateManager();
                }
            }
        }
        return mSingleton;
    }

    public Activity getForegroundActivity() {
        return (Activity) this.mForeGroundActivity.get();
    }

    private void setForegroundActivity(Activity activity) {
        this.mForeGroundActivity = new WeakReference<>(activity);
    }

    public void setFragmentScreenName(Object fragment, String fragmentScreenName) {
        try {
            Method getParentFragmentMethod = fragment.getClass().getMethod("getParentFragment", new Class[0]);
            if (getParentFragmentMethod == null) {
                return;
            }
            if (getParentFragmentMethod.invoke(fragment, new Object[0]) == null) {
                this.mCurrentFragmentName = fragmentScreenName;
                SALog.i(TAG, "setFragmentScreenName | " + fragmentScreenName + " is not nested fragment and set");
                return;
            }
            SALog.i(TAG, "setFragmentScreenName | " + fragmentScreenName + " is nested fragment and ignored");
        } catch (Exception e) {
        }
    }

    public String getFragmentScreenName() {
        return this.mCurrentFragmentName;
    }

    public int getCurrentRootWindowsHashCode() {
        WeakReference<Activity> weakReference;
        Activity activity;
        Window window;
        if (!(this.mCurrentRootWindowsHashCode != -1 || (weakReference = this.mForeGroundActivity) == null || weakReference.get() == null || (activity = (Activity) this.mForeGroundActivity.get()) == null || (window = activity.getWindow()) == null || !window.isActive())) {
            this.mCurrentRootWindowsHashCode = window.getDecorView().hashCode();
        }
        return this.mCurrentRootWindowsHashCode;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setForegroundActivity(activity);
        if (!activity.isChild()) {
            this.mCurrentRootWindowsHashCode = -1;
        }
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        setForegroundActivity(activity);
        View decorView = null;
        try {
            Window window = activity.getWindow();
            if (window != null) {
                decorView = window.getDecorView();
            }
            if (SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled() && decorView != null) {
                monitorViewTreeChange(decorView);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (!activity.isChild() && decorView != null) {
            this.mCurrentRootWindowsHashCode = decorView.hashCode();
        }
    }

    public void onActivityPaused(Activity activity) {
        Window window;
        if (SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled() && (window = activity.getWindow()) != null && window.isActive()) {
            unRegisterViewTreeChange(window.getDecorView());
        }
        if (!activity.isChild()) {
            this.mCurrentRootWindowsHashCode = -1;
        }
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
        ViewTreeStatusObservable.getInstance().clearWebViewCache();
        ViewUtil.clear();
    }

    private void unRegisterViewTreeChange(View root) {
        try {
            int i = R.id.sensors_analytics_tag_view_tree_observer_listeners;
            if (root.getTag(i) != null) {
                ViewTreeStatusObservable observable = ViewTreeStatusObservable.getInstance();
                if (Build.VERSION.SDK_INT < 16) {
                    root.getViewTreeObserver().removeGlobalOnLayoutListener(observable);
                } else {
                    root.getViewTreeObserver().removeOnGlobalLayoutListener(observable);
                }
                root.getViewTreeObserver().removeOnGlobalFocusChangeListener(observable);
                root.getViewTreeObserver().removeOnScrollChangedListener(observable);
                root.setTag(i, (Object) null);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void monitorViewTreeChange(View root) {
        try {
            int i = R.id.sensors_analytics_tag_view_tree_observer_listeners;
            if (root.getTag(i) == null) {
                root.getViewTreeObserver().addOnGlobalLayoutListener(ViewTreeStatusObservable.getInstance());
                root.getViewTreeObserver().addOnScrollChangedListener(ViewTreeStatusObservable.getInstance());
                root.getViewTreeObserver().addOnGlobalFocusChangeListener(ViewTreeStatusObservable.getInstance());
                root.setTag(i, true);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
