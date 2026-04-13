package com.sensorsdata.analytics.android.sdk.autotrack;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import com.sensorsdata.analytics.android.sdk.R;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.FragmentCacheUtil;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SAFragmentUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.WeakSet;
import java.util.Set;
import org.json.JSONObject;

public class FragmentViewScreenCallbacks implements SAFragmentLifecycleCallbacks {
    private static final String TAG = "SA.FragmentViewScreenCallbacks";
    private final Set<Object> mPageFragments = new WeakSet();

    public void onCreate(Object object) {
    }

    public void onViewCreated(Object object, View rootView, Bundle bundle) {
        Window window;
        try {
            String fragmentName = object.getClass().getName();
            int i = R.id.sensors_analytics_tag_view_fragment_name;
            rootView.setTag(i, fragmentName);
            if (rootView instanceof ViewGroup) {
                traverseView(fragmentName, (ViewGroup) rootView);
            }
            Activity activity = AopUtil.getActivityFromContext(rootView.getContext(), rootView);
            if (!(activity == null || (window = activity.getWindow()) == null)) {
                window.getDecorView().getRootView().setTag(i, "");
            }
            FragmentCacheUtil.setFragmentToCache(fragmentName, object);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void onStart(Object object) {
    }

    public void onResume(Object object) {
        try {
            if (isFragmentValid(object)) {
                trackFragmentAppViewScreen(object);
                this.mPageFragments.add(object);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void onPause(Object object) {
        if (object != null) {
            this.mPageFragments.remove(object);
        }
    }

    public void onStop(Object object) {
    }

    public void onHiddenChanged(Object object, boolean hidden) {
        if (object == null) {
            try {
                SALog.d(TAG, "fragment is null,return");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } else if (hidden) {
            this.mPageFragments.remove(object);
            SALog.d(TAG, "fragment hidden is true,return");
        } else if (isFragmentValid(object)) {
            trackFragmentAppViewScreen(object);
            this.mPageFragments.add(object);
        }
    }

    public void setUserVisibleHint(Object object, boolean isVisibleToUser) {
        if (object == null) {
            try {
                SALog.d(TAG, "object is null");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } else if (!isVisibleToUser) {
            this.mPageFragments.remove(object);
            SALog.d(TAG, "fragment isVisibleToUser is false,return");
        } else if (isFragmentValid(object)) {
            trackFragmentAppViewScreen(object);
            this.mPageFragments.add(object);
        }
    }

    private void trackFragmentAppViewScreen(Object fragment) {
        JSONObject otherProperties;
        try {
            JSONObject properties = new JSONObject();
            AopUtil.getScreenNameAndTitleFromFragment(properties, fragment, (Activity) null);
            AppStateManager.getInstance().setFragmentScreenName(fragment, properties.optString(AopConstants.SCREEN_NAME));
            if ((fragment instanceof ScreenAutoTracker) && (otherProperties = ((ScreenAutoTracker) fragment).getTrackProperties()) != null) {
                SensorsDataUtils.mergeJSONObject(otherProperties, properties);
            }
            SensorsDataAPI.sharedInstance().trackViewScreen(SensorsDataUtils.getScreenUrl(fragment), SADataHelper.appendLibMethodAutoTrack(properties));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private boolean isFragmentValid(Object fragment) {
        if (fragment == null) {
            SALog.d(TAG, "fragment is null,return");
            return false;
        } else if (SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN)) {
            SALog.d(TAG, "AutoTrackEventTypeIgnored,return");
            return false;
        } else if (!SensorsDataAPI.sharedInstance().isTrackFragmentAppViewScreenEnabled()) {
            SALog.d(TAG, "TrackFragmentAppViewScreenEnabled is false,return");
            return false;
        } else if ("com.bumptech.glide.manager.SupportRequestManagerFragment".equals(fragment.getClass().getCanonicalName())) {
            SALog.d(TAG, "fragment is SupportRequestManagerFragment,return");
            return false;
        } else if (!SensorsDataAPI.sharedInstance().isFragmentAutoTrackAppViewScreen(fragment.getClass())) {
            SALog.d(TAG, "fragment class ignored,return");
            return false;
        } else if (this.mPageFragments.contains(fragment)) {
            SALog.d(TAG, "pageFragment contains,return");
            return false;
        } else if (SAFragmentUtils.isFragmentVisible(fragment)) {
            return true;
        } else {
            SALog.d(TAG, "fragment is not visible,return");
            return false;
        }
    }

    private static void traverseView(String fragmentName, ViewGroup root) {
        try {
            if (TextUtils.isEmpty(fragmentName)) {
                return;
            }
            if (root != null) {
                int childCount = root.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = root.getChildAt(i);
                    child.setTag(R.id.sensors_analytics_tag_view_fragment_name, fragmentName);
                    if ((child instanceof ViewGroup) && !(child instanceof ListView) && !(child instanceof GridView) && !(child instanceof Spinner) && !(child instanceof RadioGroup)) {
                        traverseView(fragmentName, (ViewGroup) child);
                    }
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
