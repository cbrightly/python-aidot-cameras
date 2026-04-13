package com.sensorsdata.analytics.android.sdk.autotrack;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataExceptionHandler;
import com.sensorsdata.analytics.android.sdk.autotrack.utils.AutoTrackUtils;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.SAFragmentUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentPageLeaveCallbacks implements SAFragmentLifecycleCallbacks, SensorsDataExceptionHandler.SAExceptionListener {
    private static final String START_TIME = "sa_start_time";
    private List<Class<?>> mIgnoreList;
    private final boolean mIsEmpty;
    private final HashMap<Integer, JSONObject> mResumedFragments = new HashMap<>();

    public FragmentPageLeaveCallbacks(List<Class<?>> ignoreList) {
        if (ignoreList == null || ignoreList.isEmpty()) {
            this.mIsEmpty = true;
            return;
        }
        this.mIgnoreList = ignoreList;
        this.mIsEmpty = false;
    }

    public void onCreate(Object object) {
    }

    public void onViewCreated(Object object, View rootView, Bundle bundle) {
    }

    public void onStart(Object object) {
    }

    public void onResume(Object object) {
        if (!ignorePage(object) && SAFragmentUtils.isFragmentVisible(object)) {
            trackFragmentStart(object);
        }
    }

    public void onPause(Object object) {
        try {
            if (this.mResumedFragments.containsKey(Integer.valueOf(object.hashCode()))) {
                trackAppPageLeave(object);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void onStop(Object object) {
    }

    public void onHiddenChanged(Object object, boolean hidden) {
        if (ignorePage(object)) {
            return;
        }
        if (SAFragmentUtils.isFragmentVisible(object)) {
            trackFragmentStart(object);
        } else {
            trackAppPageLeave(object);
        }
    }

    public void setUserVisibleHint(Object object, boolean isVisibleToUser) {
        if (ignorePage(object)) {
            return;
        }
        if (SAFragmentUtils.isFragmentVisible(object)) {
            trackFragmentStart(object);
        } else {
            trackAppPageLeave(object);
        }
    }

    private void trackAppPageLeave(Object object) {
        try {
            int hashCode = object.hashCode();
            if (this.mResumedFragments.containsKey(Integer.valueOf(hashCode))) {
                JSONObject properties = this.mResumedFragments.get(Integer.valueOf(hashCode));
                long startTime = properties == null ? 0 : properties.optLong(START_TIME);
                String referrer = properties == null ? "" : properties.optString("$referrer");
                JSONObject properties2 = new JSONObject();
                AopUtil.getScreenNameAndTitleFromFragment(properties2, object, (Activity) null);
                properties2.put(START_TIME, startTime);
                properties2.put("$url", (Object) SensorsDataUtils.getScreenUrl(object));
                if (!TextUtils.isEmpty(referrer)) {
                    properties2.put("$referrer", (Object) referrer);
                }
                this.mResumedFragments.remove(Integer.valueOf(hashCode));
                trackPageLeaveEvent(properties2);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void trackFragmentStart(Object object) {
        try {
            JSONObject properties = new JSONObject();
            properties.put(START_TIME, SystemClock.elapsedRealtime());
            String url = SensorsDataUtils.getScreenUrl(object);
            properties.put("$url", (Object) url);
            String referrer = AutoTrackUtils.getLastScreenUrl();
            if (!TextUtils.isEmpty(referrer)) {
                properties.put("$referrer", (Object) referrer);
            }
            AopUtil.getScreenNameAndTitleFromFragment(properties, object, (Activity) null);
            this.mResumedFragments.put(Integer.valueOf(object.hashCode()), properties);
            AutoTrackUtils.setLastScreenUrl(url);
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public void uncaughtException(Thread t, Throwable e) {
        try {
            Iterator<Integer> keyCodes = this.mResumedFragments.keySet().iterator();
            while (keyCodes.hasNext()) {
                JSONObject properties = this.mResumedFragments.get(Integer.valueOf(keyCodes.next().intValue()));
                if (properties != null) {
                    trackPageLeaveEvent(properties);
                    keyCodes.remove();
                }
            }
        } catch (Exception exception) {
            SALog.printStackTrace(exception);
        }
    }

    private void trackPageLeaveEvent(JSONObject properties) {
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
