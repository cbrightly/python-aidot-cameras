package com.sensorsdata.analytics.android.sdk.autotrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataActivityLifecycleCallbacks;
import com.sensorsdata.analytics.android.sdk.SensorsDataExceptionHandler;
import com.sensorsdata.analytics.android.sdk.autotrack.utils.AutoTrackUtils;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityPageLeaveCallbacks implements SensorsDataActivityLifecycleCallbacks.SAActivityLifecycleCallbacks, SensorsDataExceptionHandler.SAExceptionListener {
    private static final String START_TIME = "sa_start_time";
    private final String DIALOG_ACTIVITY = "com.sensorsdata.sf.ui.view.DialogActivity";
    private List<Class<?>> mIgnoreList;
    private final boolean mIsEmpty;
    private final HashMap<Integer, JSONObject> mResumedActivities = new HashMap<>();

    public ActivityPageLeaveCallbacks(List<Class<?>> ignoreList) {
        if (ignoreList == null || ignoreList.isEmpty()) {
            this.mIsEmpty = true;
            return;
        }
        this.mIgnoreList = ignoreList;
        this.mIsEmpty = false;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        if (!ignorePage(activity)) {
            trackActivityStart(activity);
        }
    }

    public void onActivityPaused(Activity activity) {
        try {
            int hashCode = activity.hashCode();
            if (this.mResumedActivities.containsKey(Integer.valueOf(hashCode))) {
                JSONObject properties = this.mResumedActivities.get(Integer.valueOf(hashCode));
                String referrer = properties == null ? "" : properties.optString("$referrer");
                long startTime = properties == null ? 0 : properties.optLong(START_TIME);
                JSONObject properties2 = AopUtil.buildTitleAndScreenName(activity);
                properties2.put(START_TIME, startTime);
                properties2.put("$url", (Object) SensorsDataUtils.getScreenUrl(activity));
                if (!TextUtils.isEmpty(referrer)) {
                    properties2.put("$referrer", (Object) referrer);
                }
                trackAppPageLeave(properties2);
                this.mResumedActivities.remove(Integer.valueOf(hashCode));
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onNewIntent(Intent intent) {
    }

    public void uncaughtException(Thread t, Throwable e) {
        try {
            Iterator<Integer> keyCodes = this.mResumedActivities.keySet().iterator();
            while (keyCodes.hasNext()) {
                JSONObject properties = this.mResumedActivities.get(Integer.valueOf(keyCodes.next().intValue()));
                if (properties != null) {
                    trackAppPageLeave(properties);
                    keyCodes.remove();
                }
            }
        } catch (Exception exception) {
            SALog.printStackTrace(exception);
        }
    }

    private void trackActivityStart(Activity activity) {
        try {
            if (!"com.sensorsdata.sf.ui.view.DialogActivity".equals(activity.getClass().getCanonicalName())) {
                JSONObject properties = AopUtil.buildTitleAndScreenName(activity);
                String url = SensorsDataUtils.getScreenUrl(activity);
                properties.put("$url", (Object) url);
                String referrer = AutoTrackUtils.getLastScreenUrl();
                if (!properties.has("$referrer") && !TextUtils.isEmpty(referrer)) {
                    properties.put("$referrer", (Object) referrer);
                }
                properties.put(START_TIME, SystemClock.elapsedRealtime());
                this.mResumedActivities.put(Integer.valueOf(activity.hashCode()), properties);
                AutoTrackUtils.setLastScreenUrl(url);
            }
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    private void trackAppPageLeave(JSONObject properties) {
        try {
            long resumeTime = properties.optLong(START_TIME);
            properties.remove(START_TIME);
            double duration = TimeUtils.duration(resumeTime, SystemClock.elapsedRealtime());
            if (duration >= 0.05d) {
                properties.put("event_duration", duration);
                SensorsDataAPI.sharedInstance().trackInternal("$AppPageLeave", properties);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private boolean ignorePage(Object fragment) {
        if (!this.mIsEmpty) {
            return this.mIgnoreList.contains(fragment.getClass());
        }
        return false;
    }
}
