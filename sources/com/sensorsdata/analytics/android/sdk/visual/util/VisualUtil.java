package com.sensorsdata.analytics.android.sdk.visual.util;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import com.sensorsdata.analytics.android.sdk.R;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.ReflectUtil;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo;
import com.sensorsdata.analytics.android.sdk.visual.snap.Pathfinder;
import com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache;
import org.json.JSONObject;

public class VisualUtil {
    public static int getVisibility(View view) {
        if (!(view instanceof Spinner) && ViewUtil.isViewSelfVisible(view) && view.isShown()) {
            return 0;
        }
        return 8;
    }

    public static boolean isSupportElementContent(View view) {
        if (Build.VERSION.SDK_INT < 14 || (view instanceof SeekBar) || (view instanceof RatingBar) || (view instanceof Switch)) {
            return false;
        }
        return true;
    }

    public static boolean isForbiddenClick(View v) {
        if (ViewUtil.instanceOfWebView(v) || (v instanceof AdapterView)) {
            return true;
        }
        if (!(v instanceof TextView)) {
            return false;
        }
        TextView textView = (TextView) v;
        if (Build.VERSION.SDK_INT < 15 || !textView.isTextSelectable() || textView.hasOnClickListeners()) {
            return false;
        }
        return true;
    }

    public static boolean isSupportClick(View v) {
        ViewParent parent = v.getParent();
        if ((parent instanceof AdapterView) || ViewUtil.instanceOfRecyclerView(parent) || (v instanceof RatingBar) || (v instanceof SeekBar)) {
            return true;
        }
        return false;
    }

    public static int getChildIndex(ViewParent parent, View child) {
        try {
            if (!(parent instanceof ViewGroup)) {
                return -1;
            }
            ViewGroup viewParent = (ViewGroup) parent;
            String childIdName = AopUtil.getViewId(child);
            String childClassName = SnapCache.getInstance().getCanonicalName(child.getClass());
            int index = 0;
            for (int i = 0; i < viewParent.getChildCount(); i++) {
                View brother = viewParent.getChildAt(i);
                if (Pathfinder.hasClassName(brother, childClassName)) {
                    String brotherIdName = AopUtil.getViewId(brother);
                    if (childIdName != null && !childIdName.equals(brotherIdName)) {
                        index++;
                    } else if (brother == child) {
                        return index;
                    } else {
                        index++;
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return -1;
        }
    }

    public static JSONObject getScreenNameAndTitle(View view, SnapInfo info) {
        if (view == null) {
            return null;
        }
        Activity activity = AopUtil.getActivityFromContext(view.getContext(), view);
        if (activity == null) {
            activity = AppStateManager.getInstance().getForegroundActivity();
        }
        if (activity == null || activity.getWindow() == null || !activity.getWindow().isActive()) {
            return null;
        }
        JSONObject object = new JSONObject();
        Object fragment = AopUtil.getFragmentFromView(view, activity);
        if (fragment != null) {
            AopUtil.getScreenNameAndTitleFromFragment(object, fragment, activity);
            if (info == null || info.hasFragment) {
                return object;
            }
            info.hasFragment = true;
            return object;
        }
        JSONObject object2 = AopUtil.buildTitleAndScreenName(activity);
        mergeRnScreenNameAndTitle(object2, view);
        return object2;
    }

    public static void mergeRnScreenNameAndTitle(JSONObject jsonObject) {
        mergeRnScreenNameAndTitle(jsonObject, (View) null);
    }

    public static void mergeRnScreenNameAndTitle(JSONObject jsonObject, View view) {
        Object isRNView;
        try {
            String properties = (String) ReflectUtil.callStaticMethod(ReflectUtil.getCurrentClass(new String[]{"com.sensorsdata.analytics.utils.RNViewUtils"}), "getVisualizeProperties", new Object[0]);
            if (!TextUtils.isEmpty(properties)) {
                JSONObject object = new JSONObject(properties);
                if (view == null || !object.optBoolean("isSetRNViewTag", false) || ((isRNView = view.getTag(R.id.sensors_analytics_tag_view_rn_key)) != null && ((Boolean) isRNView).booleanValue())) {
                    Object isRNView2 = object.optString(AopConstants.SCREEN_NAME);
                    String rnActivityTitle = object.optString(AopConstants.TITLE);
                    if (jsonObject.has(AopConstants.SCREEN_NAME)) {
                        jsonObject.put(AopConstants.SCREEN_NAME, isRNView2);
                    }
                    if (jsonObject.has(AopConstants.TITLE)) {
                        jsonObject.put(AopConstants.TITLE, (Object) rnActivityTitle);
                    }
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
