package com.sensorsdata.analytics.android.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.R;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataFragmentTitle;
import com.sensorsdata.analytics.android.sdk.visual.ViewTreeStatusObservable;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class AopUtil {
    private static final String TAG = "SA.AopUtil";
    private static List<String> sOSViewPackage = new LinkedList<String>() {
        {
            add("android##widget");
            add("android##support##v7##widget");
            add("android##support##design##widget");
            add("android##support##text##emoji##widget");
            add("androidx##appcompat##widget");
            add("androidx##emoji##widget");
            add("androidx##cardview##widget");
            add("com##google##android##material");
        }
    };

    public static String traverseView(StringBuilder stringBuilder, ViewGroup root) {
        if (stringBuilder == null) {
            try {
                stringBuilder = new StringBuilder();
            } catch (Throwable e) {
                SALog.d(TAG, e.getMessage());
                return stringBuilder != null ? stringBuilder.toString() : "";
            }
        }
        if (root == null) {
            return stringBuilder.toString();
        }
        int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = root.getChildAt(i);
            if (child != null) {
                if (child.getVisibility() == 0) {
                    if (child instanceof ViewGroup) {
                        traverseView(stringBuilder, (ViewGroup) child);
                    } else if (!isViewIgnored(child)) {
                        String viewText = getViewText(child);
                        if (!TextUtils.isEmpty(viewText)) {
                            stringBuilder.append(viewText);
                            stringBuilder.append("-");
                        }
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String getViewText(View child) {
        CharSequence viewText;
        Method method;
        if (child == null || (child instanceof EditText)) {
            return "";
        }
        String text = SnapCache.getInstance().getViewText(child);
        if (text != null) {
            return text;
        }
        Class<?> switchCompatClass = null;
        try {
            switchCompatClass = ReflectUtil.getClassByName("androidx.appcompat.widget.SwitchCompat");
        } catch (Exception e) {
        }
        if (switchCompatClass == null) {
            try {
                switchCompatClass = ReflectUtil.getClassByName("androidx.appcompat.widget.SwitchCompat");
            } catch (Exception e2) {
            }
        }
        CharSequence viewText2 = null;
        try {
            if (child instanceof CheckBox) {
                viewText = ((CheckBox) child).getText();
            } else if (switchCompatClass != null && switchCompatClass.isInstance(child)) {
                if (((CompoundButton) child).isChecked()) {
                    method = child.getClass().getMethod("getTextOn", new Class[0]);
                } else {
                    method = child.getClass().getMethod("getTextOff", new Class[0]);
                }
                viewText = (String) method.invoke(child, new Object[0]);
            } else if (child instanceof RadioButton) {
                viewText = ((RadioButton) child).getText();
            } else if (child instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) child;
                if (toggleButton.isChecked()) {
                    viewText = toggleButton.getTextOn();
                } else {
                    viewText = toggleButton.getTextOff();
                }
            } else if (child instanceof Button) {
                viewText = ((Button) child).getText();
            } else if (child instanceof CheckedTextView) {
                viewText = ((CheckedTextView) child).getText();
            } else if (child instanceof TextView) {
                viewText = ((TextView) child).getText();
            } else if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                if (!TextUtils.isEmpty(imageView.getContentDescription())) {
                    viewText2 = imageView.getContentDescription().toString();
                }
            } else {
                viewText = child.getContentDescription();
            }
            if ((viewText == null || viewText.equals("")) && (child instanceof TextView)) {
                viewText = ((TextView) child).getHint();
            }
            if (viewText != null) {
                String text2 = viewText.toString();
                SnapCache.getInstance().setViewText(child, text2);
                return text2;
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
        return "";
    }

    public static Activity getActivityFromContext(Context context, View view) {
        Object object;
        Activity activity = null;
        if (context == null) {
            return null;
        }
        try {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else if (context instanceof ContextWrapper) {
                while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
                    context = ((ContextWrapper) context).getBaseContext();
                }
                if (context instanceof Activity) {
                    activity = (Activity) context;
                }
            }
            if (activity != null || view == null || (object = view.getTag(R.id.sensors_analytics_tag_view_activity)) == null || !(object instanceof Activity)) {
                return activity;
            }
            return (Activity) object;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public static void getScreenNameAndTitleFromFragment(JSONObject properties, Object fragment, Activity activity) {
        SensorsDataFragmentTitle sensorsDataFragmentTitle;
        String screenName = null;
        String title = null;
        try {
            if (fragment instanceof ScreenAutoTracker) {
                JSONObject trackProperties = ((ScreenAutoTracker) fragment).getTrackProperties();
                if (trackProperties != null) {
                    if (trackProperties.has(AopConstants.SCREEN_NAME)) {
                        screenName = trackProperties.optString(AopConstants.SCREEN_NAME);
                    }
                    if (trackProperties.has(AopConstants.TITLE)) {
                        title = trackProperties.optString(AopConstants.TITLE);
                    }
                    SensorsDataUtils.mergeJSONObject(trackProperties, properties);
                }
            }
            boolean isTitleNull = TextUtils.isEmpty(title);
            boolean isScreenNameNull = TextUtils.isEmpty(screenName);
            if (isTitleNull && fragment.getClass().isAnnotationPresent(SensorsDataFragmentTitle.class) && (sensorsDataFragmentTitle = (SensorsDataFragmentTitle) fragment.getClass().getAnnotation(SensorsDataFragmentTitle.class)) != null) {
                title = sensorsDataFragmentTitle.title();
            }
            boolean isTitleNull2 = TextUtils.isEmpty(title);
            if (isTitleNull2 || isScreenNameNull) {
                if (activity == null) {
                    activity = getActivityFromFragment(fragment);
                }
                if (activity != null) {
                    if (isTitleNull2) {
                        title = SensorsDataUtils.getActivityTitle(activity);
                    }
                    if (isScreenNameNull) {
                        String screenName2 = fragment.getClass().getCanonicalName();
                        screenName = String.format(Locale.CHINA, "%s|%s", new Object[]{activity.getClass().getCanonicalName(), screenName2});
                    }
                }
            }
            if (!TextUtils.isEmpty(title)) {
                properties.put(AopConstants.TITLE, (Object) title);
            }
            if (TextUtils.isEmpty(screenName)) {
                screenName = fragment.getClass().getCanonicalName();
            }
            properties.put(AopConstants.SCREEN_NAME, (Object) screenName);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public static Activity getActivityFromFragment(Object fragment) {
        if (Build.VERSION.SDK_INT < 11) {
            return null;
        }
        try {
            Method getActivityMethod = fragment.getClass().getMethod("getActivity", new Class[0]);
            if (getActivityMethod != null) {
                return (Activity) getActivityMethod.invoke(fragment, new Object[0]);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject buildTitleAndScreenName(Activity activity) {
        JSONObject trackProperties;
        JSONObject propertyJSON = new JSONObject();
        try {
            propertyJSON.put(AopConstants.SCREEN_NAME, (Object) activity.getClass().getCanonicalName());
            String activityTitle = getActivityTitle(activity);
            if (!TextUtils.isEmpty(activityTitle)) {
                propertyJSON.put(AopConstants.TITLE, (Object) activityTitle);
            }
            if ((activity instanceof ScreenAutoTracker) && (trackProperties = ((ScreenAutoTracker) activity).getTrackProperties()) != null) {
                SensorsDataUtils.mergeJSONObject(trackProperties, propertyJSON);
            }
            return propertyJSON;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return new JSONObject();
        }
    }

    public static JSONObject buildTitleNoAutoTrackerProperties(Activity activity) {
        JSONObject trackProperties;
        JSONObject propertyJSON = new JSONObject();
        try {
            propertyJSON.put(AopConstants.SCREEN_NAME, (Object) activity.getClass().getCanonicalName());
            String activityTitle = getActivityTitle(activity);
            if (!TextUtils.isEmpty(activityTitle)) {
                propertyJSON.put(AopConstants.TITLE, (Object) activityTitle);
            }
            if ((activity instanceof ScreenAutoTracker) && (trackProperties = ((ScreenAutoTracker) activity).getTrackProperties()) != null) {
                if (trackProperties.has(AopConstants.SCREEN_NAME)) {
                    propertyJSON.put(AopConstants.SCREEN_NAME, (Object) trackProperties.optString(AopConstants.SCREEN_NAME));
                }
                if (trackProperties.has(AopConstants.TITLE)) {
                    propertyJSON.put(AopConstants.TITLE, (Object) trackProperties.optString(AopConstants.TITLE));
                }
            }
            return propertyJSON;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return new JSONObject();
        }
    }

    public static String getCompoundButtonText(View view) {
        Method method;
        try {
            if (((CompoundButton) view).isChecked()) {
                method = view.getClass().getMethod("getTextOn", new Class[0]);
            } else {
                method = view.getClass().getMethod("getTextOff", new Class[0]);
            }
            return (String) method.invoke(view, new Object[0]);
        } catch (Exception e) {
            return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    public static String getViewId(View view) {
        try {
            String idString = (String) view.getTag(R.id.sensors_analytics_tag_view_id);
            if (!TextUtils.isEmpty(idString) || !isValid(view.getId())) {
                return idString;
            }
            String idString2 = SnapCache.getInstance().getViewId(view);
            if (idString2 != null) {
                return idString2;
            }
            String idString3 = view.getContext().getResources().getResourceEntryName(view.getId());
            SnapCache.getInstance().setViewId(view, idString3);
            return idString3;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private static boolean isValid(int id) {
        return (id == -1 || (-16777216 & id) == 0 || (16711680 & id) == 0) ? false : true;
    }

    public static String getViewType(String viewName, String defaultTypeName) {
        if (TextUtils.isEmpty(viewName)) {
            return defaultTypeName;
        }
        if (!TextUtils.isEmpty(defaultTypeName) && isOSViewByPackage(viewName)) {
            return defaultTypeName;
        }
        return viewName;
    }

    public static String getViewGroupTypeByReflect(View view) {
        String viewType = SnapCache.getInstance().getCanonicalName(view.getClass());
        Class<?> compatClass = ReflectUtil.getClassByName("androidx.cardview.widget.CardView");
        if (compatClass != null && compatClass.isInstance(view)) {
            return getViewType(viewType, "CardView");
        }
        Class<?> compatClass2 = ReflectUtil.getClassByName("androidx.cardview.widget.CardView");
        if (compatClass2 != null && compatClass2.isInstance(view)) {
            return getViewType(viewType, "CardView");
        }
        Class<?> compatClass3 = ReflectUtil.getClassByName("com.google.android.material.navigation.NavigationView");
        if (compatClass3 != null && compatClass3.isInstance(view)) {
            return getViewType(viewType, "NavigationView");
        }
        Class<?> compatClass4 = ReflectUtil.getClassByName("com.google.android.material.navigation.NavigationView");
        if (compatClass4 == null || !compatClass4.isInstance(view)) {
            return viewType;
        }
        return getViewType(viewType, "NavigationView");
    }

    public static String getViewTypeByReflect(View view) {
        String viewType = SnapCache.getInstance().getCanonicalName(view.getClass());
        Class<?> compatClass = ReflectUtil.getClassByName("android.widget.Switch");
        if (compatClass != null && compatClass.isInstance(view)) {
            return getViewType(viewType, "Switch");
        }
        Class<?> compatClass2 = ReflectUtil.getClassByName("androidx.appcompat.widget.SwitchCompat");
        if (compatClass2 != null && compatClass2.isInstance(view)) {
            return getViewType(viewType, "SwitchCompat");
        }
        Class<?> compatClass3 = ReflectUtil.getClassByName("androidx.appcompat.widget.SwitchCompat");
        if (compatClass3 == null || !compatClass3.isInstance(view)) {
            return viewType;
        }
        return getViewType(viewType, "SwitchCompat");
    }

    public static boolean isViewIgnored(Class viewType) {
        if (viewType == null) {
            return true;
        }
        try {
            List<Class> mIgnoredViewTypeList = SensorsDataAPI.sharedInstance().getIgnoredViewTypeList();
            if (mIgnoredViewTypeList.isEmpty()) {
                return false;
            }
            for (Class<?> clazz : mIgnoredViewTypeList) {
                if (clazz.isAssignableFrom(viewType)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isViewIgnored(View view) {
        if (view == null) {
            return true;
        }
        try {
            List<Class> mIgnoredViewTypeList = SensorsDataAPI.sharedInstance().getIgnoredViewTypeList();
            if (mIgnoredViewTypeList != null) {
                for (Class<?> clazz : mIgnoredViewTypeList) {
                    if (clazz.isAssignableFrom(view.getClass())) {
                        return true;
                    }
                }
            }
            return "1".equals(view.getTag(R.id.sensors_analytics_tag_view_ignored));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return true;
        }
    }

    private static String getActivityTitle(Activity activity) {
        PackageManager packageManager;
        if (activity == null) {
            return null;
        }
        String activityTitle = null;
        try {
            if (!TextUtils.isEmpty(activity.getTitle())) {
                activityTitle = activity.getTitle().toString();
            }
            if (Build.VERSION.SDK_INT >= 11) {
                String toolbarTitle = SensorsDataUtils.getToolbarTitle(activity);
                if (!TextUtils.isEmpty(toolbarTitle)) {
                    activityTitle = toolbarTitle;
                }
            }
            if (!TextUtils.isEmpty(activityTitle) || (packageManager = activity.getPackageManager()) == null) {
                return activityTitle;
            }
            ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 0);
            if (!TextUtils.isEmpty(activityInfo.loadLabel(packageManager))) {
                return activityInfo.loadLabel(packageManager).toString();
            }
            return activityTitle;
        } catch (Exception e) {
            return null;
        }
    }

    public static void mergeJSONObject(JSONObject source, JSONObject dest) {
        try {
            Iterator<String> superPropertiesIterator = source.keys();
            while (superPropertiesIterator.hasNext()) {
                String key = superPropertiesIterator.next();
                Object value = source.get(key);
                if (value instanceof Date) {
                    dest.put(key, (Object) TimeUtils.formatDate((Date) value));
                } else {
                    dest.put(key, value);
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private static boolean isOSViewByPackage(String viewName) {
        if (TextUtils.isEmpty(viewName)) {
            return false;
        }
        String viewNameTemp = viewName.replace(".", "##");
        for (String OSViewPackage : sOSViewPackage) {
            if (viewNameTemp.startsWith(OSViewPackage)) {
                return true;
            }
        }
        return false;
    }

    public static boolean injectClickInfo(View view, JSONObject properties, boolean isFromUser) {
        if (view == null || properties == null) {
            return false;
        }
        try {
            if (!ViewUtil.isTrackEvent(view, isFromUser)) {
                return false;
            }
            Context context = view.getContext();
            JSONObject eventJson = new JSONObject();
            Activity activity = getActivityFromContext(context, view);
            String idString = getViewId(view);
            if (!TextUtils.isEmpty(idString)) {
                eventJson.put(AopConstants.ELEMENT_ID, (Object) idString);
            }
            ViewNode viewNode = ViewUtil.getViewContentAndType(view);
            String viewText = viewNode.getViewContent();
            if (!TextUtils.isEmpty(viewText)) {
                eventJson.put(AopConstants.ELEMENT_CONTENT, (Object) viewText);
            }
            eventJson.put(AopConstants.ELEMENT_TYPE, (Object) viewNode.getViewType());
            if (activity != null) {
                SensorsDataUtils.mergeJSONObject(buildTitleAndScreenName(activity), eventJson);
            }
            Object fragment = getFragmentFromView(view, activity);
            if (fragment != null) {
                getScreenNameAndTitleFromFragment(eventJson, fragment, activity);
            }
            JSONObject p = (JSONObject) view.getTag(R.id.sensors_analytics_tag_view_properties);
            if (p != null) {
                mergeJSONObject(p, eventJson);
            }
            JSONUtils.mergeDistinctProperty(eventJson, properties);
            return true;
        } catch (JSONException e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static Object getFragmentFromView(View view) {
        return getFragmentFromView(view, (Activity) null);
    }

    public static Object getFragmentFromView(View view, Activity activity) {
        Window window;
        if (view == null) {
            return null;
        }
        try {
            int i = R.id.sensors_analytics_tag_view_fragment_name;
            String fragmentName = (String) view.getTag(i);
            String fragmentName2 = (String) view.getTag(R.id.sensors_analytics_tag_view_fragment_name2);
            if (!TextUtils.isEmpty(fragmentName2)) {
                fragmentName = fragmentName2;
            }
            if (TextUtils.isEmpty(fragmentName)) {
                if (activity == null) {
                    activity = getActivityFromContext(view.getContext(), view);
                }
                if (!(activity == null || (window = activity.getWindow()) == null || !window.isActive() || window.getDecorView().getRootView().getTag(i) == null)) {
                    fragmentName = traverseParentViewTag(view);
                }
            }
            return FragmentCacheUtil.getFragmentFromCache(fragmentName);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public static ViewNode addViewPathProperties(Activity activity, View view, JSONObject properties) {
        if (view == null || activity == null) {
            return null;
        }
        if (properties == null) {
            try {
                properties = new JSONObject();
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
        if ((SensorsDataAPI.sharedInstance().isHeatMapEnabled() || SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled()) && (SensorsDataAPI.sharedInstance().isHeatMapActivity(activity.getClass()) || SensorsDataAPI.sharedInstance().isVisualizedAutoTrackActivity(activity.getClass()))) {
            String elementSelector = ViewUtil.getElementSelector(view);
            if (!TextUtils.isEmpty(elementSelector)) {
                properties.put(AopConstants.ELEMENT_SELECTOR, (Object) elementSelector);
            }
        }
        ViewNode viewNode = ViewTreeStatusObservable.getInstance().getViewNode(view);
        if (viewNode != null) {
            if (!TextUtils.isEmpty(viewNode.getViewPath()) && ((SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled() && SensorsDataAPI.sharedInstance().isVisualizedAutoTrackActivity(activity.getClass())) || (SensorsDataAPI.sharedInstance().isHeatMapEnabled() && SensorsDataAPI.sharedInstance().isHeatMapActivity(activity.getClass())))) {
                properties.put(AopConstants.ELEMENT_PATH, (Object) viewNode.getViewPath());
            }
            if (!TextUtils.isEmpty(viewNode.getViewPosition())) {
                properties.put(AopConstants.ELEMENT_POSITION, (Object) viewNode.getViewPosition());
            }
            return viewNode;
        }
        return null;
    }

    private static String traverseParentViewTag(View view) {
        try {
            ViewParent parentView = view.getParent();
            String fragmentName = null;
            while (TextUtils.isEmpty(fragmentName) && (parentView instanceof View)) {
                fragmentName = (String) ((View) parentView).getTag(R.id.sensors_analytics_tag_view_fragment_name);
                parentView = parentView.getParent();
            }
            return fragmentName;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return "";
        }
    }
}
