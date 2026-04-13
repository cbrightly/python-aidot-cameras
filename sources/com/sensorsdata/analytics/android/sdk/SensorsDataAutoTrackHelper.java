package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.dialog.SensorsDataDialogUtils;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.ThreadUtils;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.util.WindowHelper;
import com.sensorsdata.analytics.android.sdk.visual.WebViewVisualInterface;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorsDataAutoTrackHelper {
    private static final String TAG = "SensorsDataAutoTrackHelper";
    private static HashMap<Integer, Long> eventTimestamp = new HashMap<>();

    /* access modifiers changed from: private */
    public static boolean isDeBounceTrack(Object object) {
        long currentOnClickTimestamp = SystemClock.elapsedRealtime();
        Object targetObject = eventTimestamp.get(Integer.valueOf(object.hashCode()));
        if (targetObject != null && currentOnClickTimestamp - ((Long) targetObject).longValue() < 500) {
            return true;
        }
        eventTimestamp.put(Integer.valueOf(object.hashCode()), Long.valueOf(currentOnClickTimestamp));
        return false;
    }

    public static void trackRN(Object target, int reactTag, int s, boolean b) {
    }

    public static void trackExpandableListViewOnGroupClick(ExpandableListView expandableListView, View view, int groupPosition) {
        Context context;
        if (expandableListView != null && view != null) {
            try {
                if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK) && (context = expandableListView.getContext()) != null) {
                    Activity activity = null;
                    if (context instanceof Activity) {
                        activity = (Activity) context;
                    }
                    if (activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                        Object fragment = AopUtil.getFragmentFromView(expandableListView, activity);
                        if ((fragment == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(fragment.getClass())) && !AopUtil.isViewIgnored(ExpandableListView.class) && !AopUtil.isViewIgnored((View) expandableListView)) {
                            JSONObject properties = new JSONObject();
                            ViewNode viewNode = AopUtil.addViewPathProperties(activity, view, properties);
                            if (activity != null) {
                                SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                            }
                            String idString = AopUtil.getViewId(expandableListView);
                            if (!TextUtils.isEmpty(idString)) {
                                properties.put(AopConstants.ELEMENT_ID, (Object) idString);
                            }
                            properties.put(AopConstants.ELEMENT_TYPE, (Object) "ExpandableListView");
                            String viewText = null;
                            if (view instanceof ViewGroup) {
                                try {
                                    viewText = AopUtil.traverseView(new StringBuilder(), (ViewGroup) view);
                                    if (!TextUtils.isEmpty(viewText)) {
                                        viewText = viewText.substring(0, viewText.length() - 1);
                                    }
                                } catch (Exception e) {
                                    SALog.printStackTrace(e);
                                }
                            } else {
                                viewText = AopUtil.getViewText(view);
                            }
                            if (!TextUtils.isEmpty(viewText)) {
                                properties.put(AopConstants.ELEMENT_CONTENT, (Object) viewText);
                            }
                            if (fragment != null) {
                                AopUtil.getScreenNameAndTitleFromFragment(properties, fragment, activity);
                            }
                            JSONObject p = (JSONObject) view.getTag(R.id.sensors_analytics_tag_view_properties);
                            if (p != null) {
                                AopUtil.mergeJSONObject(p, properties);
                            }
                            ExpandableListAdapter listAdapter = expandableListView.getExpandableListAdapter();
                            if (listAdapter != null && (listAdapter instanceof SensorsExpandableListViewItemTrackProperties)) {
                                try {
                                    JSONObject jsonObject = ((SensorsExpandableListViewItemTrackProperties) listAdapter).getSensorsGroupItemTrackProperties(groupPosition);
                                    if (jsonObject != null) {
                                        AopUtil.mergeJSONObject(jsonObject, properties);
                                    }
                                } catch (JSONException e2) {
                                    SALog.printStackTrace(e2);
                                }
                            }
                            SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                        }
                    }
                }
            } catch (Exception e3) {
                SALog.printStackTrace(e3);
            }
        }
    }

    public static void trackExpandableListViewOnChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition) {
        Context context;
        JSONObject jsonObject;
        if (expandableListView != null && view != null) {
            try {
                if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK) && (context = expandableListView.getContext()) != null) {
                    Activity activity = AopUtil.getActivityFromContext(context, expandableListView);
                    if (activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                        Object fragment = AopUtil.getFragmentFromView(expandableListView, activity);
                        if ((fragment == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(fragment.getClass())) && !AopUtil.isViewIgnored(ExpandableListView.class) && !AopUtil.isViewIgnored((View) expandableListView) && !AopUtil.isViewIgnored(view)) {
                            JSONObject properties = (JSONObject) view.getTag(R.id.sensors_analytics_tag_view_properties);
                            if (properties == null) {
                                properties = new JSONObject();
                            }
                            ExpandableListAdapter listAdapter = expandableListView.getExpandableListAdapter();
                            if (!(listAdapter == null || !(listAdapter instanceof SensorsExpandableListViewItemTrackProperties) || (jsonObject = ((SensorsExpandableListViewItemTrackProperties) listAdapter).getSensorsChildItemTrackProperties(groupPosition, childPosition)) == null)) {
                                AopUtil.mergeJSONObject(jsonObject, properties);
                            }
                            ViewNode viewNode = AopUtil.addViewPathProperties(activity, view, properties);
                            if (activity != null) {
                                SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                            }
                            String idString = AopUtil.getViewId(expandableListView);
                            if (!TextUtils.isEmpty(idString)) {
                                properties.put(AopConstants.ELEMENT_ID, (Object) idString);
                            }
                            properties.put(AopConstants.ELEMENT_TYPE, (Object) "ExpandableListView");
                            String viewText = null;
                            if (view instanceof ViewGroup) {
                                try {
                                    viewText = AopUtil.traverseView(new StringBuilder(), (ViewGroup) view);
                                    if (!TextUtils.isEmpty(viewText)) {
                                        viewText = viewText.substring(0, viewText.length() - 1);
                                    }
                                } catch (Exception e) {
                                    SALog.printStackTrace(e);
                                }
                            } else {
                                viewText = AopUtil.getViewText(view);
                            }
                            if (!TextUtils.isEmpty(viewText)) {
                                properties.put(AopConstants.ELEMENT_CONTENT, (Object) viewText);
                            }
                            if (fragment != null) {
                                AopUtil.getScreenNameAndTitleFromFragment(properties, fragment, activity);
                            }
                            JSONObject p = (JSONObject) view.getTag(R.id.sensors_analytics_tag_view_properties);
                            if (p != null) {
                                AopUtil.mergeJSONObject(p, properties);
                            }
                            SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                        }
                    }
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
            }
        }
    }

    public static void trackTabHost(final String tabName) {
        try {
            ThreadUtils.getSinglePool().execute(new Runnable() {
                public void run() {
                    Context context;
                    try {
                        if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK) && !AopUtil.isViewIgnored(TabHost.class)) {
                            JSONObject properties = new JSONObject();
                            String elementContent = null;
                            View view = WindowHelper.getClickView(tabName);
                            ViewNode viewNode = null;
                            if (view != null) {
                                View currentView = view;
                                View tabHostView = null;
                                while (tabHostView == null && currentView != null && currentView.getParent() != null) {
                                    currentView = (View) currentView.getParent();
                                    if (currentView instanceof TabHost) {
                                        tabHostView = currentView;
                                    }
                                }
                                if ((tabHostView == null || !AopUtil.isViewIgnored(tabHostView)) && (context = view.getContext()) != null) {
                                    Activity activity = null;
                                    if (context instanceof Activity) {
                                        activity = (Activity) context;
                                    }
                                    if (activity != null) {
                                        if (!SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                                            SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                                            Object fragment = AopUtil.getFragmentFromView(view, activity);
                                            if (fragment != null) {
                                                if (!SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(fragment.getClass())) {
                                                    AopUtil.getScreenNameAndTitleFromFragment(properties, fragment, activity);
                                                } else {
                                                    return;
                                                }
                                            }
                                            viewNode = AopUtil.addViewPathProperties(activity, view, properties);
                                        } else {
                                            return;
                                        }
                                    }
                                    elementContent = ViewUtil.getViewContentAndType(view).getViewContent();
                                } else {
                                    return;
                                }
                            }
                            if (TextUtils.isEmpty(elementContent)) {
                                elementContent = tabName;
                            }
                            properties.put(AopConstants.ELEMENT_CONTENT, (Object) elementContent);
                            properties.put(AopConstants.ELEMENT_TYPE, (Object) "TabHost");
                            SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                        }
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:157:0x01e4, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x01e5, code lost:
        r16 = r4;
        r18 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x01eb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x01ec, code lost:
        r15 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x01f3, code lost:
        r15 = r3.getDeclaredField("customView");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x02c2, code lost:
        if (r4.getId() == -1) goto L_0x02c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00f3, code lost:
        r8 = (android.app.Activity) r14;
        r0 = r1;
        r19 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00f8, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00f9, code lost:
        r19 = r5;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0153 A[Catch:{ Exception -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0165 A[Catch:{ Exception -> 0x0166 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0169 A[Catch:{ Exception -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x01e4 A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:154:0x01de] */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x0295  */
    /* JADX WARNING: Removed duplicated region for block: B:228:0x02b3 A[Catch:{ Exception -> 0x028a }] */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x02b9  */
    /* JADX WARNING: Removed duplicated region for block: B:233:0x02be A[SYNTHETIC, Splitter:B:233:0x02be] */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x02fa A[Catch:{ Exception -> 0x02c5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void trackTabLayoutSelected(java.lang.Object r22, java.lang.Object r23) {
        /*
            r1 = r22
            r2 = r23
            java.lang.String r3 = "com.google.android.material.tabs.TabLayout$Tab"
            java.lang.String r4 = "com.google.android.material.tabs.TabLayout"
            if (r2 != 0) goto L_0x000b
            return
        L_0x000b:
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x0334 }
            boolean r0 = r0.isAutoTrackEnabled()     // Catch:{ Exception -> 0x0334 }
            if (r0 != 0) goto L_0x0016
            return
        L_0x0016:
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x0334 }
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI$AutoTrackEventType r5 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType.APP_CLICK     // Catch:{ Exception -> 0x0334 }
            boolean r0 = r0.isAutoTrackEventTypeIgnored((com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType) r5)     // Catch:{ Exception -> 0x0334 }
            if (r0 == 0) goto L_0x0023
            return
        L_0x0023:
            r5 = 0
            r6 = 0
            java.lang.Class r0 = java.lang.Class.forName(r4)     // Catch:{ Exception -> 0x002b }
            r5 = r0
            goto L_0x002c
        L_0x002b:
            r0 = move-exception
        L_0x002c:
            java.lang.Class r0 = java.lang.Class.forName(r4)     // Catch:{ Exception -> 0x0032 }
            r6 = r0
            goto L_0x0033
        L_0x0032:
            r0 = move-exception
        L_0x0033:
            if (r5 != 0) goto L_0x0038
            if (r6 != 0) goto L_0x0038
            return
        L_0x0038:
            r0 = 0
            java.lang.String r4 = "com.google.android.material.tabs.TabLayout"
            java.lang.String r7 = "mParent"
            java.lang.String r8 = "com.google.android.material.tabs.TabLayout$Tab"
            if (r5 == 0) goto L_0x0077
            java.lang.String[] r9 = new java.lang.String[]{r8}     // Catch:{ Exception -> 0x0074 }
            boolean r9 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.isInstance(r2, r9)     // Catch:{ Exception -> 0x0074 }
            if (r9 == 0) goto L_0x006d
            java.lang.String[] r9 = new java.lang.String[]{r8}     // Catch:{ Exception -> 0x0074 }
            java.lang.String[] r10 = new java.lang.String[]{r7}     // Catch:{ Exception -> 0x0074 }
            java.lang.Object r9 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.findField((java.lang.String[]) r9, (java.lang.Object) r2, (java.lang.String[]) r10)     // Catch:{ Exception -> 0x0074 }
            android.view.View r9 = (android.view.View) r9     // Catch:{ Exception -> 0x0074 }
            if (r9 == 0) goto L_0x006c
            java.lang.String[] r10 = new java.lang.String[]{r4}     // Catch:{ Exception -> 0x0074 }
            boolean r10 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.isInstance(r9, r10)     // Catch:{ Exception -> 0x0074 }
            if (r10 == 0) goto L_0x006c
            boolean r10 = com.sensorsdata.analytics.android.sdk.util.AopUtil.isViewIgnored((android.view.View) r9)     // Catch:{ Exception -> 0x0074 }
            if (r10 == 0) goto L_0x006c
            return
        L_0x006c:
            r0 = r9
        L_0x006d:
            boolean r9 = com.sensorsdata.analytics.android.sdk.util.AopUtil.isViewIgnored((java.lang.Class) r5)     // Catch:{ Exception -> 0x0074 }
            if (r9 == 0) goto L_0x0077
            return
        L_0x0074:
            r0 = move-exception
            goto L_0x0337
        L_0x0077:
            java.lang.String r9 = "parent"
            if (r6 == 0) goto L_0x00b1
            java.lang.String[] r10 = new java.lang.String[]{r8}     // Catch:{ Exception -> 0x0074 }
            boolean r10 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.isInstance(r2, r10)     // Catch:{ Exception -> 0x0074 }
            if (r10 == 0) goto L_0x00a8
            java.lang.String[] r8 = new java.lang.String[]{r8}     // Catch:{ Exception -> 0x0074 }
            java.lang.String[] r10 = new java.lang.String[]{r9}     // Catch:{ Exception -> 0x0074 }
            java.lang.Object r8 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.findField((java.lang.String[]) r8, (java.lang.Object) r2, (java.lang.String[]) r10)     // Catch:{ Exception -> 0x0074 }
            android.view.View r8 = (android.view.View) r8     // Catch:{ Exception -> 0x0074 }
            if (r8 == 0) goto L_0x00a7
            java.lang.String[] r4 = new java.lang.String[]{r4}     // Catch:{ Exception -> 0x0074 }
            boolean r4 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.isInstance(r8, r4)     // Catch:{ Exception -> 0x0074 }
            if (r4 == 0) goto L_0x00a7
            boolean r4 = com.sensorsdata.analytics.android.sdk.util.AopUtil.isViewIgnored((android.view.View) r8)     // Catch:{ Exception -> 0x0074 }
            if (r4 == 0) goto L_0x00a7
            return
        L_0x00a7:
            r0 = r8
        L_0x00a8:
            boolean r4 = com.sensorsdata.analytics.android.sdk.util.AopUtil.isViewIgnored((java.lang.Class) r6)     // Catch:{ Exception -> 0x0074 }
            if (r4 == 0) goto L_0x00af
            return
        L_0x00af:
            r4 = r0
            goto L_0x00b2
        L_0x00b1:
            r4 = r0
        L_0x00b2:
            boolean r0 = isDeBounceTrack(r23)     // Catch:{ Exception -> 0x0334 }
            if (r0 == 0) goto L_0x00b9
            return
        L_0x00b9:
            r8 = 0
            r10 = 0
            r11 = 0
            boolean r0 = r1 instanceof android.content.Context     // Catch:{ Exception -> 0x0334 }
            r12 = 0
            r14 = 1
            if (r0 == 0) goto L_0x00cd
            r0 = r1
            android.content.Context r0 = (android.content.Context) r0     // Catch:{ Exception -> 0x0074 }
            android.app.Activity r0 = com.sensorsdata.analytics.android.sdk.util.AopUtil.getActivityFromContext(r0, r12)     // Catch:{ Exception -> 0x0074 }
            r19 = r5
            goto L_0x013d
        L_0x00cd:
            java.lang.Class r0 = r22.getClass()     // Catch:{ Exception -> 0x0134 }
            java.lang.reflect.Field[] r0 = r0.getDeclaredFields()     // Catch:{ Exception -> 0x0134 }
            int r15 = r0.length     // Catch:{ Exception -> 0x0134 }
            r13 = 0
        L_0x00d7:
            if (r13 >= r15) goto L_0x012b
            r16 = r0[r13]     // Catch:{ Exception -> 0x0134 }
            r17 = r16
            r12 = r17
            r12.setAccessible(r14)     // Catch:{ Exception -> 0x0134 }
            java.lang.Object r17 = r12.get(r1)     // Catch:{ Exception -> 0x0134 }
            r18 = r17
            r14 = r18
            r18 = r0
            boolean r0 = r14 instanceof android.app.Activity     // Catch:{ Exception -> 0x0134 }
            if (r0 == 0) goto L_0x00fc
            r0 = r14
            android.app.Activity r0 = (android.app.Activity) r0     // Catch:{ Exception -> 0x00f8 }
            r8 = r0
            r0 = r1
            r19 = r5
            goto L_0x0131
        L_0x00f8:
            r0 = move-exception
            r19 = r5
            goto L_0x0137
        L_0x00fc:
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.SAFragmentUtils.isFragment(r14)     // Catch:{ Exception -> 0x0134 }
            if (r0 == 0) goto L_0x0108
            r0 = r14
            r1 = 1
            r11 = r1
            r19 = r5
            goto L_0x0131
        L_0x0108:
            boolean r0 = r14 instanceof android.view.View     // Catch:{ Exception -> 0x0134 }
            if (r0 == 0) goto L_0x011e
            r0 = r14
            android.view.View r0 = (android.view.View) r0     // Catch:{ Exception -> 0x0134 }
            android.content.Context r1 = r0.getContext()     // Catch:{ Exception -> 0x0134 }
            r19 = r5
            r5 = 0
            android.app.Activity r1 = com.sensorsdata.analytics.android.sdk.util.AopUtil.getActivityFromContext(r1, r5)     // Catch:{ Exception -> 0x011c }
            r8 = r1
            goto L_0x0120
        L_0x011c:
            r0 = move-exception
            goto L_0x0137
        L_0x011e:
            r19 = r5
        L_0x0120:
            int r13 = r13 + 1
            r1 = r22
            r0 = r18
            r5 = r19
            r12 = 0
            r14 = 1
            goto L_0x00d7
        L_0x012b:
            r18 = r0
            r19 = r5
            r0 = r22
        L_0x0131:
            r1 = r0
            r0 = r8
            goto L_0x013d
        L_0x0134:
            r0 = move-exception
            r19 = r5
        L_0x0137:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x0334 }
            r1 = r22
            r0 = r8
        L_0x013d:
            if (r0 != 0) goto L_0x0155
            if (r11 != 0) goto L_0x0155
            if (r4 == 0) goto L_0x0155
            android.content.Context r5 = r4.getContext()     // Catch:{ Exception -> 0x0166 }
            r8 = 0
            android.app.Activity r5 = com.sensorsdata.analytics.android.sdk.util.AopUtil.getActivityFromContext(r5, r8)     // Catch:{ Exception -> 0x0166 }
            r0 = r5
            java.lang.Object r5 = com.sensorsdata.analytics.android.sdk.util.AopUtil.getFragmentFromView(r4, r0)     // Catch:{ Exception -> 0x0166 }
            if (r5 == 0) goto L_0x0155
            r1 = r5
            r11 = 1
        L_0x0155:
            if (r0 == 0) goto L_0x0169
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r5 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x0166 }
            java.lang.Class r8 = r0.getClass()     // Catch:{ Exception -> 0x0166 }
            boolean r5 = r5.isActivityAutoTrackAppClickIgnored(r8)     // Catch:{ Exception -> 0x0166 }
            if (r5 == 0) goto L_0x0169
            return
        L_0x0166:
            r0 = move-exception
            goto L_0x0337
        L_0x0169:
            if (r11 == 0) goto L_0x017a
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r5 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x0166 }
            java.lang.Class r8 = r1.getClass()     // Catch:{ Exception -> 0x0166 }
            boolean r5 = r5.isActivityAutoTrackAppClickIgnored(r8)     // Catch:{ Exception -> 0x0166 }
            if (r5 == 0) goto L_0x017a
            return
        L_0x017a:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x0330 }
            r5.<init>()     // Catch:{ Exception -> 0x0330 }
            java.lang.String r8 = "$element_type"
            java.lang.String r12 = "TabLayout"
            r5.put((java.lang.String) r8, (java.lang.Object) r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 == 0) goto L_0x0194
            if (r0 != 0) goto L_0x018f
            android.app.Activity r8 = com.sensorsdata.analytics.android.sdk.util.AopUtil.getActivityFromFragment(r1)     // Catch:{ Exception -> 0x0166 }
            r0 = r8
        L_0x018f:
            com.sensorsdata.analytics.android.sdk.util.AopUtil.getScreenNameAndTitleFromFragment(r5, r1, r0)     // Catch:{ Exception -> 0x0166 }
            r8 = r0
            goto L_0x019e
        L_0x0194:
            if (r0 == 0) goto L_0x019d
            org.json.JSONObject r8 = com.sensorsdata.analytics.android.sdk.util.AopUtil.buildTitleAndScreenName(r0)     // Catch:{ Exception -> 0x0166 }
            com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.mergeJSONObject(r8, r5)     // Catch:{ Exception -> 0x0166 }
        L_0x019d:
            r8 = r0
        L_0x019e:
            r12 = 0
            r13 = 0
            java.lang.Class r0 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x01a6 }
            r12 = r0
            goto L_0x01a7
        L_0x01a6:
            r0 = move-exception
        L_0x01a7:
            java.lang.Class r0 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x01ad }
            r13 = r0
            goto L_0x01ae
        L_0x01ad:
            r0 = move-exception
        L_0x01ae:
            if (r12 == 0) goto L_0x01b3
            r0 = r12
            r3 = r0
            goto L_0x01b5
        L_0x01b3:
            r0 = r13
            r3 = r0
        L_0x01b5:
            if (r3 == 0) goto L_0x031a
            r14 = 0
            java.lang.String r0 = "getText"
            r22 = r1
            r15 = 0
            java.lang.Class[] r1 = new java.lang.Class[r15]     // Catch:{ NoSuchMethodException -> 0x01c5 }
            java.lang.reflect.Method r0 = r3.getMethod(r0, r1)     // Catch:{ NoSuchMethodException -> 0x01c5 }
            r14 = r0
            goto L_0x01ca
        L_0x01c5:
            r0 = move-exception
            goto L_0x01ca
        L_0x01c7:
            r0 = move-exception
            r22 = r1
        L_0x01ca:
            java.lang.String r1 = "$element_content"
            if (r14 == 0) goto L_0x01da
            r15 = 0
            java.lang.Object[] r0 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x0334 }
            java.lang.Object r0 = r14.invoke(r2, r0)     // Catch:{ Exception -> 0x0334 }
            if (r0 == 0) goto L_0x01da
            r5.put((java.lang.String) r1, (java.lang.Object) r0)     // Catch:{ Exception -> 0x0334 }
        L_0x01da:
            if (r8 == 0) goto L_0x0313
            java.lang.String r0 = "mCustomView"
            java.lang.reflect.Field r0 = r3.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x01eb, Exception -> 0x01e4 }
            r15 = r0
            goto L_0x01fa
        L_0x01e4:
            r0 = move-exception
            r16 = r4
            r18 = r6
            goto L_0x030f
        L_0x01eb:
            r0 = move-exception
            r15 = r0
            java.lang.String r0 = "customView"
            java.lang.reflect.Field r0 = r3.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x01f5, Exception -> 0x01e4 }
            r15 = r0
            goto L_0x01fa
        L_0x01f5:
            r0 = move-exception
            r16 = 0
            r15 = r16
        L_0x01fa:
            r0 = 0
            if (r15 == 0) goto L_0x0269
            r16 = r4
            r4 = 1
            r15.setAccessible(r4)     // Catch:{ Exception -> 0x0262 }
            java.lang.Object r4 = r15.get(r2)     // Catch:{ Exception -> 0x0262 }
            android.view.View r4 = (android.view.View) r4     // Catch:{ Exception -> 0x0262 }
            if (r4 == 0) goto L_0x025d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0254 }
            r0.<init>()     // Catch:{ Exception -> 0x0254 }
            r18 = r6
            boolean r6 = r4 instanceof android.view.ViewGroup     // Catch:{ Exception -> 0x0250 }
            if (r6 == 0) goto L_0x023b
            r6 = r4
            android.view.ViewGroup r6 = (android.view.ViewGroup) r6     // Catch:{ Exception -> 0x0250 }
            java.lang.String r6 = com.sensorsdata.analytics.android.sdk.util.AopUtil.traverseView(r0, r6)     // Catch:{ Exception -> 0x0250 }
            boolean r20 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x0250 }
            if (r20 != 0) goto L_0x0236
            int r20 = r6.length()     // Catch:{ Exception -> 0x0250 }
            r21 = r0
            r17 = 1
            int r0 = r20 + -1
            r20 = r10
            r10 = 0
            java.lang.String r0 = r6.substring(r10, r0)     // Catch:{ Exception -> 0x024e }
            r6 = r0
            goto L_0x0244
        L_0x0236:
            r21 = r0
            r20 = r10
            goto L_0x0244
        L_0x023b:
            r21 = r0
            r20 = r10
            java.lang.String r0 = com.sensorsdata.analytics.android.sdk.util.AopUtil.getViewText(r4)     // Catch:{ Exception -> 0x024e }
            r6 = r0
        L_0x0244:
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x024e }
            if (r0 != 0) goto L_0x024d
            r5.put((java.lang.String) r1, (java.lang.Object) r6)     // Catch:{ Exception -> 0x024e }
        L_0x024d:
            goto L_0x0270
        L_0x024e:
            r0 = move-exception
            goto L_0x0259
        L_0x0250:
            r0 = move-exception
            r20 = r10
            goto L_0x0259
        L_0x0254:
            r0 = move-exception
            r18 = r6
            r20 = r10
        L_0x0259:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x028a }
            goto L_0x0270
        L_0x025d:
            r18 = r6
            r20 = r10
            goto L_0x0270
        L_0x0262:
            r0 = move-exception
            r18 = r6
            r20 = r10
            goto L_0x030f
        L_0x0269:
            r16 = r4
            r18 = r6
            r20 = r10
            r4 = r0
        L_0x0270:
            r1 = 0
            java.lang.String r0 = "view"
            java.lang.reflect.Field r0 = r3.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x028f }
            r6 = r0
            r10 = 1
            r6.setAccessible(r10)     // Catch:{ NoSuchFieldException -> 0x028f }
            java.lang.Object r0 = r6.get(r2)     // Catch:{ IllegalAccessException -> 0x0285 }
            android.view.View r0 = (android.view.View) r0     // Catch:{ IllegalAccessException -> 0x0285 }
            r1 = r0
            goto L_0x0289
        L_0x0285:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ NoSuchFieldException -> 0x028f }
        L_0x0289:
            goto L_0x0293
        L_0x028a:
            r0 = move-exception
            r10 = r20
            goto L_0x030f
        L_0x028f:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x028a }
        L_0x0293:
            if (r1 != 0) goto L_0x02b1
            java.lang.String r0 = "mView"
            java.lang.reflect.Field r0 = r3.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x02ad }
            r6 = r0
            r10 = 1
            r6.setAccessible(r10)     // Catch:{ NoSuchFieldException -> 0x02ad }
            java.lang.Object r0 = r6.get(r2)     // Catch:{ IllegalAccessException -> 0x02a8 }
            android.view.View r0 = (android.view.View) r0     // Catch:{ IllegalAccessException -> 0x02a8 }
            r1 = r0
            goto L_0x02ac
        L_0x02a8:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ NoSuchFieldException -> 0x02ad }
        L_0x02ac:
            goto L_0x02b1
        L_0x02ad:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x028a }
        L_0x02b1:
            if (r1 == 0) goto L_0x02b9
            com.sensorsdata.analytics.android.sdk.visual.model.ViewNode r0 = com.sensorsdata.analytics.android.sdk.util.AopUtil.addViewPathProperties(r8, r1, r5)     // Catch:{ Exception -> 0x028a }
            r10 = r0
            goto L_0x02bb
        L_0x02b9:
            r10 = r20
        L_0x02bb:
            r6 = -1
            if (r4 == 0) goto L_0x02c7
            int r0 = r4.getId()     // Catch:{ Exception -> 0x02c5 }
            if (r0 != r6) goto L_0x02e0
            goto L_0x02c7
        L_0x02c5:
            r0 = move-exception
            goto L_0x030f
        L_0x02c7:
            java.lang.reflect.Field r0 = r3.getDeclaredField(r7)     // Catch:{ NoSuchFieldException -> 0x02cd }
            r15 = r0
            goto L_0x02d5
        L_0x02cd:
            r0 = move-exception
            r7 = r0
            r0 = r7
            java.lang.reflect.Field r7 = r3.getDeclaredField(r9)     // Catch:{ Exception -> 0x02c5 }
            r15 = r7
        L_0x02d5:
            r7 = 1
            r15.setAccessible(r7)     // Catch:{ Exception -> 0x02c5 }
            java.lang.Object r0 = r15.get(r2)     // Catch:{ Exception -> 0x02c5 }
            android.view.View r0 = (android.view.View) r0     // Catch:{ Exception -> 0x02c5 }
            r4 = r0
        L_0x02e0:
            if (r4 == 0) goto L_0x02ff
            int r0 = r4.getId()     // Catch:{ Exception -> 0x02c5 }
            if (r0 == r6) goto L_0x02ff
            android.content.res.Resources r0 = r8.getResources()     // Catch:{ Exception -> 0x02c5 }
            int r6 = r4.getId()     // Catch:{ Exception -> 0x02c5 }
            java.lang.String r0 = r0.getResourceEntryName(r6)     // Catch:{ Exception -> 0x02c5 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x02c5 }
            if (r6 != 0) goto L_0x02ff
            java.lang.String r6 = "$element_id"
            r5.put((java.lang.String) r6, (java.lang.Object) r0)     // Catch:{ Exception -> 0x02c5 }
        L_0x02ff:
            if (r4 == 0) goto L_0x030e
            int r0 = com.sensorsdata.analytics.android.sdk.R.id.sensors_analytics_tag_view_properties     // Catch:{ Exception -> 0x02c5 }
            java.lang.Object r0 = r4.getTag(r0)     // Catch:{ Exception -> 0x02c5 }
            org.json.JSONObject r0 = (org.json.JSONObject) r0     // Catch:{ Exception -> 0x02c5 }
            if (r0 == 0) goto L_0x030e
            com.sensorsdata.analytics.android.sdk.util.AopUtil.mergeJSONObject(r0, r5)     // Catch:{ Exception -> 0x02c5 }
        L_0x030e:
            goto L_0x0324
        L_0x030f:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x0334 }
            goto L_0x0324
        L_0x0313:
            r16 = r4
            r18 = r6
            r20 = r10
            goto L_0x0322
        L_0x031a:
            r22 = r1
            r16 = r4
            r18 = r6
            r20 = r10
        L_0x0322:
            r10 = r20
        L_0x0324:
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x0334 }
            java.lang.String r1 = "$AppClick"
            r0.trackAutoEvent(r1, r5, r10)     // Catch:{ Exception -> 0x0334 }
            r1 = r22
            goto L_0x033a
        L_0x0330:
            r0 = move-exception
            r22 = r1
            goto L_0x0337
        L_0x0334:
            r0 = move-exception
            r1 = r22
        L_0x0337:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x033a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackTabLayoutSelected(java.lang.Object, java.lang.Object):void");
    }

    public static void trackMenuItem(MenuItem menuItem) {
        trackMenuItem((Object) null, menuItem);
    }

    public static void trackMenuItem(final Object object, final MenuItem menuItem) {
        try {
            ThreadUtils.getSinglePool().execute(new Runnable() {
                public void run() {
                    try {
                        if (menuItem != null && SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK) && !AopUtil.isViewIgnored(MenuItem.class) && !SensorsDataAutoTrackHelper.isDeBounceTrack(menuItem)) {
                            Context context = null;
                            Object obj = object;
                            if (obj != null && (obj instanceof Context)) {
                                context = (Context) obj;
                            }
                            View view = WindowHelper.getClickView(menuItem);
                            if (context == null && view != null) {
                                context = view.getContext();
                            }
                            Activity activity = null;
                            if (context != null) {
                                activity = AopUtil.getActivityFromContext(context, (View) null);
                            }
                            if (activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                                String idString = null;
                                if (context != null) {
                                    try {
                                        idString = context.getResources().getResourceEntryName(menuItem.getItemId());
                                    } catch (Exception e) {
                                        SALog.printStackTrace(e);
                                    }
                                }
                                JSONObject properties = new JSONObject();
                                if (activity != null) {
                                    SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                                }
                                if (!TextUtils.isEmpty(idString)) {
                                    properties.put(AopConstants.ELEMENT_ID, (Object) idString);
                                }
                                String elementContent = null;
                                if (!TextUtils.isEmpty(menuItem.getTitle())) {
                                    elementContent = menuItem.getTitle().toString();
                                }
                                ViewNode viewNode = null;
                                if (view != null) {
                                    if (TextUtils.isEmpty(elementContent)) {
                                        elementContent = ViewUtil.getViewContentAndType(view).getViewContent();
                                    }
                                    viewNode = AopUtil.addViewPathProperties(activity, view, properties);
                                }
                                properties.put(AopConstants.ELEMENT_CONTENT, (Object) elementContent);
                                properties.put(AopConstants.ELEMENT_TYPE, (Object) "MenuItem");
                                SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                            }
                        }
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void trackRadioGroup(RadioGroup view, int checkedId) {
        Context context;
        if (view != null) {
            try {
                View childView = view.findViewById(checkedId);
                if (childView == null) {
                    return;
                }
                if (childView.isPressed()) {
                    if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK) && (context = view.getContext()) != null) {
                        Activity activity = AopUtil.getActivityFromContext(context, view);
                        if (activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                            Object fragment = AopUtil.getFragmentFromView(view, activity);
                            if ((fragment == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(fragment.getClass())) && !AopUtil.isViewIgnored((View) view)) {
                                JSONObject properties = new JSONObject();
                                String idString = AopUtil.getViewId(view);
                                if (!TextUtils.isEmpty(idString)) {
                                    properties.put(AopConstants.ELEMENT_ID, (Object) idString);
                                }
                                if (activity != null) {
                                    SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                                }
                                Object obj = "RadioButton";
                                properties.put(AopConstants.ELEMENT_TYPE, (Object) AopUtil.getViewType(childView.getClass().getCanonicalName(), "RadioButton"));
                                int checkedRadioButtonId = view.getCheckedRadioButtonId();
                                ViewNode viewNode = null;
                                if (activity != null) {
                                    try {
                                        RadioButton radioButton = (RadioButton) activity.findViewById(checkedRadioButtonId);
                                        if (radioButton != null) {
                                            if (!TextUtils.isEmpty(radioButton.getText())) {
                                                String viewText = radioButton.getText().toString();
                                                if (!TextUtils.isEmpty(viewText)) {
                                                    properties.put(AopConstants.ELEMENT_CONTENT, (Object) viewText);
                                                }
                                            }
                                            viewNode = AopUtil.addViewPathProperties(activity, radioButton, properties);
                                        }
                                    } catch (Exception e) {
                                        SALog.printStackTrace(e);
                                    }
                                }
                                if (fragment != null) {
                                    AopUtil.getScreenNameAndTitleFromFragment(properties, fragment, activity);
                                }
                                JSONObject p = (JSONObject) view.getTag(R.id.sensors_analytics_tag_view_properties);
                                if (p != null) {
                                    AopUtil.mergeJSONObject(p, properties);
                                }
                                SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
            }
        }
    }

    public static void trackDialog(DialogInterface dialogInterface, int whichButton) {
        Dialog dialog;
        Activity activity;
        Class<?> currentAlertDialogClass;
        DialogInterface dialogInterface2 = dialogInterface;
        int i = whichButton;
        try {
            if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK)) {
                if (dialogInterface2 instanceof Dialog) {
                    dialog = (Dialog) dialogInterface2;
                } else {
                    dialog = null;
                }
                if (dialog != null && !isDeBounceTrack(dialog)) {
                    Activity activity2 = AopUtil.getActivityFromContext(dialog.getContext(), (View) null);
                    if (activity2 == null) {
                        activity = dialog.getOwnerActivity();
                    } else {
                        activity = activity2;
                    }
                    if ((activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) && !AopUtil.isViewIgnored(Dialog.class)) {
                        JSONObject properties = new JSONObject();
                        try {
                            Window window = dialog.getWindow();
                            if (window != null && window.isActive()) {
                                String idString = (String) dialog.getWindow().getDecorView().getTag(R.id.sensors_analytics_tag_view_id);
                                if (!TextUtils.isEmpty(idString)) {
                                    properties.put(AopConstants.ELEMENT_ID, (Object) idString);
                                }
                            }
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                        }
                        if (activity != null) {
                            SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                        }
                        VisualUtil.mergeRnScreenNameAndTitle(properties);
                        properties.put(AopConstants.ELEMENT_TYPE, (Object) "Dialog");
                        Class<?> supportAlertDialogClass = null;
                        Class<?> androidXAlertDialogClass = null;
                        try {
                            supportAlertDialogClass = Class.forName("androidx.appcompat.app.AlertDialog");
                        } catch (Exception e2) {
                        }
                        try {
                            androidXAlertDialogClass = Class.forName("androidx.appcompat.app.AlertDialog");
                        } catch (Exception e3) {
                        }
                        if (supportAlertDialogClass != null || androidXAlertDialogClass != null) {
                            if (supportAlertDialogClass != null) {
                                currentAlertDialogClass = supportAlertDialogClass;
                            } else {
                                currentAlertDialogClass = androidXAlertDialogClass;
                            }
                            ViewNode viewNode = null;
                            if (dialog instanceof AlertDialog) {
                                AlertDialog alertDialog = (AlertDialog) dialog;
                                Button button = alertDialog.getButton(i);
                                if (button != null) {
                                    if (!TextUtils.isEmpty(button.getText())) {
                                        properties.put(AopConstants.ELEMENT_CONTENT, (Object) button.getText());
                                    }
                                    viewNode = AopUtil.addViewPathProperties(activity, button, properties);
                                } else {
                                    ListView listView = alertDialog.getListView();
                                    if (listView != null) {
                                        Object object = listView.getAdapter().getItem(i);
                                        if (object != null) {
                                            AlertDialog alertDialog2 = alertDialog;
                                            if (object instanceof String) {
                                                properties.put(AopConstants.ELEMENT_CONTENT, object);
                                            }
                                        }
                                        View clickView = listView.getChildAt(i);
                                        if (clickView != null) {
                                            viewNode = AopUtil.addViewPathProperties(activity, clickView, properties);
                                        }
                                    }
                                }
                            } else if (currentAlertDialogClass.isInstance(dialog)) {
                                Button button2 = null;
                                try {
                                    Method getButtonMethod = dialog.getClass().getMethod("getButton", new Class[]{Integer.TYPE});
                                    if (getButtonMethod != null) {
                                        button2 = (Button) getButtonMethod.invoke(dialog, new Object[]{Integer.valueOf(whichButton)});
                                    }
                                } catch (Exception e4) {
                                }
                                if (button2 != null) {
                                    if (!TextUtils.isEmpty(button2.getText())) {
                                        properties.put(AopConstants.ELEMENT_CONTENT, (Object) button2.getText());
                                    }
                                    viewNode = AopUtil.addViewPathProperties(activity, button2, properties);
                                } else {
                                    try {
                                        Method getListViewMethod = dialog.getClass().getMethod("getListView", new Class[0]);
                                        if (getListViewMethod != null) {
                                            ListView listView2 = (ListView) getListViewMethod.invoke(dialog, new Object[0]);
                                            if (listView2 != null) {
                                                Object object2 = listView2.getAdapter().getItem(i);
                                                if (object2 != null) {
                                                    Method method = getListViewMethod;
                                                    if (object2 instanceof String) {
                                                        properties.put(AopConstants.ELEMENT_CONTENT, object2);
                                                    }
                                                }
                                                View clickView2 = listView2.getChildAt(i);
                                                if (clickView2 != null) {
                                                    viewNode = AopUtil.addViewPathProperties(activity, clickView2, properties);
                                                }
                                            }
                                        }
                                    } catch (Exception e5) {
                                    }
                                }
                                SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                            }
                            SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                        }
                    }
                }
            }
        } catch (Exception e6) {
            SALog.printStackTrace(e6);
        }
    }

    public static void trackListView(AdapterView<?> adapterView, View view, int position) {
        Context context;
        if (view != null) {
            try {
                if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK) && (context = view.getContext()) != null) {
                    Activity activity = AopUtil.getActivityFromContext(context, view);
                    if (activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                        Object fragment = AopUtil.getFragmentFromView(adapterView, activity);
                        if ((fragment == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(fragment.getClass())) && !AopUtil.isViewIgnored((View) adapterView)) {
                            JSONObject properties = new JSONObject();
                            if (adapterView instanceof ListView) {
                                properties.put(AopConstants.ELEMENT_TYPE, (Object) "ListView");
                                if (AopUtil.isViewIgnored(ListView.class)) {
                                    return;
                                }
                            } else if (adapterView instanceof GridView) {
                                properties.put(AopConstants.ELEMENT_TYPE, (Object) "GridView");
                                if (AopUtil.isViewIgnored(GridView.class)) {
                                    return;
                                }
                            } else if (adapterView instanceof Spinner) {
                                properties.put(AopConstants.ELEMENT_TYPE, (Object) "Spinner");
                                if (AopUtil.isViewIgnored(Spinner.class)) {
                                    return;
                                }
                            }
                            String idString = AopUtil.getViewId(adapterView);
                            if (!TextUtils.isEmpty(idString)) {
                                properties.put(AopConstants.ELEMENT_ID, (Object) idString);
                            }
                            Object adapter = adapterView.getAdapter();
                            if (adapter instanceof HeaderViewListAdapter) {
                                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                            }
                            if (adapter instanceof SensorsAdapterViewItemTrackProperties) {
                                try {
                                    JSONObject jsonObject = ((SensorsAdapterViewItemTrackProperties) adapter).getSensorsItemTrackProperties(position);
                                    if (jsonObject != null) {
                                        AopUtil.mergeJSONObject(jsonObject, properties);
                                    }
                                } catch (JSONException e) {
                                    SALog.printStackTrace(e);
                                }
                            }
                            ViewNode viewNode = AopUtil.addViewPathProperties(activity, view, properties);
                            if (activity != null) {
                                SensorsDataUtils.mergeJSONObject(AopUtil.buildTitleAndScreenName(activity), properties);
                            }
                            String viewText = null;
                            if (view instanceof ViewGroup) {
                                try {
                                    viewText = AopUtil.traverseView(new StringBuilder(), (ViewGroup) view);
                                    if (!TextUtils.isEmpty(viewText)) {
                                        viewText = viewText.substring(0, viewText.length() - 1);
                                    }
                                } catch (Exception e2) {
                                    SALog.printStackTrace(e2);
                                }
                            } else {
                                viewText = AopUtil.getViewText(view);
                            }
                            if (!TextUtils.isEmpty(viewText)) {
                                properties.put(AopConstants.ELEMENT_CONTENT, (Object) viewText);
                            }
                            if (fragment != null) {
                                AopUtil.getScreenNameAndTitleFromFragment(properties, fragment, activity);
                            }
                            JSONObject p = (JSONObject) view.getTag(R.id.sensors_analytics_tag_view_properties);
                            if (p != null) {
                                AopUtil.mergeJSONObject(p, properties);
                            }
                            SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, viewNode);
                        }
                    }
                }
            } catch (Exception e3) {
                SALog.printStackTrace(e3);
            }
        }
    }

    public static void trackDrawerOpened(View view) {
        if (view != null) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(AopConstants.ELEMENT_CONTENT, (Object) "Open");
                SensorsDataAPI.sharedInstance().setViewProperties(view, jsonObject);
                trackViewOnClick(view);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void trackDrawerClosed(View view) {
        if (view != null) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(AopConstants.ELEMENT_CONTENT, (Object) "Close");
                SensorsDataAPI.sharedInstance().setViewProperties(view, jsonObject);
                trackViewOnClick(view);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void trackViewOnClick(View view) {
        if (view != null) {
            trackViewOnClick(view, view.isPressed());
        }
    }

    public static void trackViewOnClick(View view, boolean isFromUser) {
        if (view != null) {
            try {
                if (SensorsDataAPI.sharedInstance().isAutoTrackEnabled() && !SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_CLICK)) {
                    Activity activity = AopUtil.getActivityFromContext(view.getContext(), view);
                    if (activity == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(activity.getClass())) {
                        Object fragment = AopUtil.getFragmentFromView(view, activity);
                        if ((fragment == null || !SensorsDataAPI.sharedInstance().isActivityAutoTrackAppClickIgnored(fragment.getClass())) && !AopUtil.isViewIgnored(view) && !SensorsDataUtils.isDoubleClick(view)) {
                            JSONObject properties = new JSONObject();
                            if (AopUtil.injectClickInfo(view, properties, isFromUser)) {
                                SensorsDataAPI.sharedInstance().trackAutoEvent(AopConstants.APP_CLICK_EVENT_NAME, properties, AopUtil.addViewPathProperties(activity, view, properties));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static void track(String eventName, String properties) {
        try {
            if (!TextUtils.isEmpty(eventName)) {
                JSONObject pro = null;
                if (!TextUtils.isEmpty(properties)) {
                    try {
                        pro = new JSONObject(properties);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
                SensorsDataAPI.sharedInstance().trackInternal(eventName, pro);
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }

    public static void showChannelDebugActiveDialog(final Activity activity) {
        SensorsDataDialogUtils.showDialog(activity, "成功开启调试模式", "此模式下不需要卸载 App，点击“激活”按钮可反复触发激活", "激活", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SensorsDataAPI.sharedInstance().trackChannelDebugInstallation();
                SensorsDataAutoTrackHelper.showChannelDebugActiveDialog(activity);
            }
        }, "取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SensorsDataDialogUtils.startLaunchActivity(activity);
            }
        });
    }

    public static void loadUrl(View webView, String url) {
        loadUrl2(webView, url);
        invokeWebViewLoad(webView, "loadUrl", new Object[]{url}, new Class[]{String.class});
    }

    public static void loadUrl2(View webView, String url) {
        if (webView == null) {
            SALog.i(TAG, "WebView has not initialized.");
        } else {
            setupH5Bridge(webView);
        }
    }

    public static void loadUrl(View webView, String url, Map<String, String> additionalHttpHeaders) {
        loadUrl2(webView, url, additionalHttpHeaders);
        invokeWebViewLoad(webView, "loadUrl", new Object[]{url, additionalHttpHeaders}, new Class[]{String.class, Map.class});
    }

    public static void loadUrl2(View webView, String url, Map<String, String> map) {
        if (webView == null) {
            SALog.i(TAG, "WebView has not initialized.");
        } else {
            setupH5Bridge(webView);
        }
    }

    public static void loadData(View webView, String data, String mimeType, String encoding) {
        Class<String> cls = String.class;
        loadData2(webView, data, mimeType, encoding);
        invokeWebViewLoad(webView, "loadData", new Object[]{data, mimeType, encoding}, new Class[]{cls, cls, cls});
    }

    public static void loadData2(View webView, String data, String mimeType, String encoding) {
        if (webView == null) {
            SALog.i(TAG, "WebView has not initialized.");
        } else {
            setupH5Bridge(webView);
        }
    }

    public static void loadDataWithBaseURL(View webView, String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        Class<String> cls = String.class;
        loadDataWithBaseURL2(webView, baseUrl, data, mimeType, encoding, historyUrl);
        invokeWebViewLoad(webView, "loadDataWithBaseURL", new Object[]{baseUrl, data, mimeType, encoding, historyUrl}, new Class[]{cls, cls, cls, cls, cls});
    }

    public static void loadDataWithBaseURL2(View webView, String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        if (webView == null) {
            SALog.i(TAG, "WebView has not initialized.");
        } else {
            setupH5Bridge(webView);
        }
    }

    public static void postUrl(View webView, String url, byte[] postData) {
        postUrl2(webView, url, postData);
        invokeWebViewLoad(webView, "postUrl", new Object[]{url, postData}, new Class[]{String.class, byte[].class});
    }

    public static void postUrl2(View webView, String url, byte[] postData) {
        if (webView == null) {
            SALog.i(TAG, "WebView has not initialized.");
        } else {
            setupH5Bridge(webView);
        }
    }

    private static void setupH5Bridge(View webView) {
        if (!(SensorsDataAPI.sharedInstance() instanceof SensorsDataAPIEmptyImplementation)) {
            if (isSupportJellyBean()) {
                SensorsDataAPI.sharedInstance();
                if (AbstractSensorsDataAPI.getConfigOptions() != null) {
                    SensorsDataAPI.sharedInstance();
                    if (AbstractSensorsDataAPI.getConfigOptions().isAutoTrackWebView) {
                        setupWebView(webView);
                    }
                }
            }
            if (isSupportJellyBean()) {
                addWebViewVisualInterface(webView);
            }
        }
    }

    private static void invokeWebViewLoad(View webView, String methodName, Object[] params, Class[] paramTypes) {
        if (webView == null) {
            SALog.i(TAG, "WebView has not initialized.");
            return;
        }
        try {
            webView.getClass().getMethod(methodName, paramTypes).invoke(webView, params);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    static void addWebViewVisualInterface(View webView) {
        if (webView != null) {
            int i = R.id.sensors_analytics_tag_view_webview_visual;
            if (webView.getTag(i) == null) {
                webView.setTag(i, new Object());
                addJavascriptInterface(webView, new WebViewVisualInterface(webView), "SensorsData_App_Visual_Bridge");
            }
        }
    }

    private static boolean isSupportJellyBean() {
        if (Build.VERSION.SDK_INT >= 17 || AbstractSensorsDataAPI.getConfigOptions().isWebViewSupportJellyBean) {
            return true;
        }
        SALog.d(TAG, "For applications targeted to API level JELLY_BEAN or below, this feature NOT SUPPORTED");
        return false;
    }

    private static void setupWebView(View webView) {
        if (webView != null) {
            int i = R.id.sensors_analytics_tag_view_webview;
            if (webView.getTag(i) == null) {
                webView.setTag(i, new Object());
                addJavascriptInterface(webView, new AppWebViewInterface(SensorsDataAPI.sharedInstance().getContext(), (JSONObject) null, false, webView), "SensorsData_APP_New_H5_Bridge");
            }
        }
    }

    private static void addJavascriptInterface(View webView, Object obj, String interfaceName) {
        try {
            Class<?> clazz = webView.getClass();
            try {
                Object settings = clazz.getMethod("getSettings", new Class[0]).invoke(webView, new Object[0]);
                if (settings != null) {
                    settings.getClass().getMethod("setJavaScriptEnabled", new Class[]{Boolean.TYPE}).invoke(settings, new Object[]{true});
                }
            } catch (Exception e) {
            }
            clazz.getMethod("addJavascriptInterface", new Class[]{Object.class, String.class}).invoke(webView, new Object[]{obj, interfaceName});
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }
}
