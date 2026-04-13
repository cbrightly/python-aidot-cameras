package com.sensorsdata.analytics.android.sdk.visual;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.view.Window;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import com.sensorsdata.analytics.android.sdk.util.DeviceUtils;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.util.WindowHelper;
import com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo;
import com.sensorsdata.analytics.android.sdk.visual.model.WebNode;
import com.sensorsdata.analytics.android.sdk.visual.snap.Caller;
import com.sensorsdata.analytics.android.sdk.visual.snap.PropertyDescription;
import com.sensorsdata.analytics.android.sdk.visual.snap.ResourceIds;
import com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache;
import com.sensorsdata.analytics.android.sdk.visual.snap.SoftWareCanvas;
import com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViewSnapshot {
    private static final int JS_NOT_INTEGRATED_ALERT_TIME_OUT = 5000;
    private static final int MAX_CLASS_NAME_CACHE_SIZE = 255;
    private static final String TAG = "SA.ViewSnapshot";
    private AlertRunnable mAlertRunnable;
    private final ClassNameCache mClassnameCache;
    private final Handler mMainThreadHandler;
    private final List<PropertyDescription> mProperties;
    private final ResourceIds mResourceIds;
    private final RootViewFinder mRootViewFinder;
    /* access modifiers changed from: private */
    public SnapInfo mSnapInfo = new SnapInfo();

    public ViewSnapshot(List<PropertyDescription> properties, ResourceIds resourceIds, Handler mainThreadHandler) {
        this.mProperties = properties;
        this.mResourceIds = resourceIds;
        this.mMainThreadHandler = mainThreadHandler;
        this.mRootViewFinder = new RootViewFinder();
        this.mClassnameCache = new ClassNameCache(255);
    }

    public SnapInfo snapshots(OutputStream out, StringBuilder lastImageHash) {
        int infoCount;
        List<RootViewInfo> infoList;
        int infoCount2;
        CachedBitmap cachedBitmap;
        OutputStream outputStream = out;
        long startSnapshot = System.currentTimeMillis();
        FutureTask futureTask = new FutureTask(this.mRootViewFinder);
        this.mMainThreadHandler.post(futureTask);
        OutputStream writer = new BufferedOutputStream(outputStream);
        List<RootViewInfo> infoList2 = Collections.emptyList();
        writer.write(Constants.ARRAY_TYPE.getBytes());
        try {
            infoList2 = (List) futureTask.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            SALog.i(TAG, "Screenshot interrupted, no screenshot will be sent.", e);
        } catch (TimeoutException e2) {
            SALog.i(TAG, "Screenshot took more than 2 second to be scheduled and executed. No screenshot will be sent.", e2);
        } catch (ExecutionException e3) {
            SALog.i(TAG, "Exception thrown during screenshot attempt", e3);
        } catch (Throwable th) {
            futureTask.cancel(true);
            this.mMainThreadHandler.removeCallbacks(futureTask);
            throw th;
        }
        futureTask.cancel(true);
        this.mMainThreadHandler.removeCallbacks(futureTask);
        String screenName = null;
        String activityTitle = null;
        int infoCount3 = infoList2.size();
        SALog.i(TAG, "infoCount:" + infoCount3 + ",time:" + (System.currentTimeMillis() - startSnapshot));
        int i = 0;
        while (i < infoCount3) {
            RootViewInfo info = infoList2.get(i);
            if (i > 0) {
                writer.write(",".getBytes());
            }
            if (info == null || (cachedBitmap = info.screenshot) == null) {
                infoList = infoList2;
                infoCount2 = infoCount3;
            } else if (isSnapShotUpdated(cachedBitmap.getImageHash(), lastImageHash) || i > 0) {
                writer.write("{".getBytes());
                writer.write("\"activity\":".getBytes());
                String screenName2 = info.screenName;
                String activityTitle2 = info.activityTitle;
                writer.write(JSONObject.quote(info.screenName).getBytes());
                writer.write(",".getBytes());
                writer.write("\"scale\":".getBytes());
                infoList = infoList2;
                String activityTitle3 = activityTitle2;
                writer.write(String.format("%s", new Object[]{Float.valueOf(info.scale)}).getBytes());
                writer.write(",".getBytes());
                writer.write("\"serialized_objects\":".getBytes());
                try {
                    JSONObject jsonRootObject = new JSONObject();
                    jsonRootObject.put("rootObject", info.rootView.hashCode());
                    JSONArray jsonObjects = new JSONArray();
                    snapshotViewHierarchy(jsonObjects, info.rootView);
                    jsonRootObject.put("objects", (Object) jsonObjects);
                    writer.write(jsonRootObject.toString().getBytes());
                    StringBuilder sb = new StringBuilder();
                    JSONObject jSONObject = jsonRootObject;
                    sb.append("snapshotViewHierarchy:");
                    infoCount = infoCount3;
                    try {
                        sb.append(System.currentTimeMillis() - startSnapshot);
                        SALog.i(TAG, sb.toString());
                    } catch (Exception e4) {
                        e = e4;
                    }
                } catch (Exception e5) {
                    e = e5;
                    infoCount = infoCount3;
                    SALog.printStackTrace(e);
                    writer.write(",".getBytes());
                    writer.write("\"image_hash\":".getBytes());
                    writer.write(JSONObject.quote(info.screenshot.getImageHash()).getBytes());
                    writer.write(",".getBytes());
                    writer.write("\"screenshot\":".getBytes());
                    writer.flush();
                    info.screenshot.writeBitmapJSON(Bitmap.CompressFormat.PNG, 70, outputStream);
                    writer.write("}".getBytes());
                    screenName = screenName2;
                    activityTitle = activityTitle3;
                    i++;
                    infoList2 = infoList;
                    infoCount3 = infoCount;
                }
                writer.write(",".getBytes());
                writer.write("\"image_hash\":".getBytes());
                writer.write(JSONObject.quote(info.screenshot.getImageHash()).getBytes());
                writer.write(",".getBytes());
                writer.write("\"screenshot\":".getBytes());
                writer.flush();
                info.screenshot.writeBitmapJSON(Bitmap.CompressFormat.PNG, 70, outputStream);
                writer.write("}".getBytes());
                screenName = screenName2;
                activityTitle = activityTitle3;
                i++;
                infoList2 = infoList;
                infoCount3 = infoCount;
            } else {
                infoList = infoList2;
                infoCount2 = infoCount3;
            }
            writer.write("{}".getBytes());
            i++;
            infoList2 = infoList;
            infoCount3 = infoCount;
        }
        writer.write("]".getBytes());
        writer.flush();
        SnapInfo snapInfo = this.mSnapInfo;
        snapInfo.screenName = screenName;
        snapInfo.activityTitle = activityTitle;
        return snapInfo;
    }

    private void getVisibleRect(View view, Rect rect, boolean fullscreen) {
        if (fullscreen) {
            view.getGlobalVisibleRect(rect);
            return;
        }
        int[] offset = new int[2];
        view.getLocationOnScreen(offset);
        SnapCache.getInstance().setLocalVisibleRect(view, Boolean.valueOf(view.getLocalVisibleRect(rect)));
        rect.offset(offset[0], offset[1]);
    }

    private void snapshotViewHierarchy(JSONArray j, View rootView) {
        if (Build.VERSION.SDK_INT >= 11) {
            reset();
            snapshotView(j, rootView, 0);
            WebNodesManager.getInstance().setHasWebView(this.mSnapInfo.isWebView);
        }
    }

    private void reset() {
        this.mSnapInfo = new SnapInfo();
    }

    public static class AlertRunnable implements Runnable {
        private String url;

        AlertRunnable(String url2) {
            this.url = url2;
        }

        public void run() {
            if (WebNodesManager.getInstance().getWebNodes(this.url) == null) {
                SALog.i(ViewSnapshot.TAG, "H5 页面未集成 Web JS SDK");
                WebNodesManager.getInstance().handlerFailure(this.url, "{\"callType\":\"app_alert\",\"data\":[{\"title\":\"当前页面无法进行可视化全埋点\",\"message\":\"此页面未集成 Web JS SDK 或者 Web JS SDK 版本过低，请集成最新版 Web JS SDK\",\"link_text\":\"配置文档\",\"link_url\":\"https://manual.sensorsdata.cn/sa/latest/tech_sdk_client_web_use-7545346.html\"}]}");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x02c8  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02e5  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x030a  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x031b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0330  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0364  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0377  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x038e  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x03c8  */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x0320 A[ADDED_TO_REGION, EDGE_INSN: B:157:0x0320->B:116:0x0320 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:167:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x02a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void snapshotView(org.json.JSONArray r23, android.view.View r24, int r25) {
        /*
            r22 = this;
            r1 = r22
            r2 = r23
            r3 = r24
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.ViewUtil.isViewSelfVisible(r24)
            if (r0 == 0) goto L_0x03c2
            r4 = 0
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r0 = r1.mSnapInfo
            int r5 = r0.elementLevel
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.ViewUtil.instanceOfWebView(r24)
            r6 = 1
            if (r0 == 0) goto L_0x00eb
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r0 = r1.mSnapInfo
            r0.isWebView = r6
            java.util.concurrent.CountDownLatch r0 = new java.util.concurrent.CountDownLatch
            r0.<init>(r6)
            r7 = r0
            com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$1 r0 = new com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$1     // Catch:{ Exception -> 0x002b }
            r0.<init>(r3, r7)     // Catch:{ Exception -> 0x002b }
            r3.post(r0)     // Catch:{ Exception -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x002f:
            r8 = 500(0x1f4, double:2.47E-321)
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0037 }
            r7.await(r8, r0)     // Catch:{ InterruptedException -> 0x0037 }
            goto L_0x003b
        L_0x0037:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x003b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r8 = "WebView url: "
            r0.append(r8)
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r8 = r1.mSnapInfo
            java.lang.String r8 = r8.webViewUrl
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r8 = "SA.ViewSnapshot"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r8, (java.lang.String) r0)
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r0 = r1.mSnapInfo
            java.lang.String r0 = r0.webViewUrl
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00eb
            com.sensorsdata.analytics.android.sdk.visual.WebNodesManager r0 = com.sensorsdata.analytics.android.sdk.visual.WebNodesManager.getInstance()
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r8 = r1.mSnapInfo
            java.lang.String r8 = r8.webViewUrl
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo r0 = r0.getWebNodes(r8)
            if (r0 == 0) goto L_0x00d1
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo$Status r8 = r0.getStatus()
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo$Status r9 = com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo.Status.SUCCESS
            if (r8 != r9) goto L_0x00c0
            java.util.List r8 = r0.getWebNodes()
            if (r8 == 0) goto L_0x00bf
            int r9 = r8.size()
            if (r9 <= 0) goto L_0x00bf
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r4 = r9
            java.util.Iterator r9 = r8.iterator()
        L_0x008b:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00bf
            java.lang.Object r10 = r9.next()
            com.sensorsdata.analytics.android.sdk.visual.model.WebNode r10 = (com.sensorsdata.analytics.android.sdk.visual.model.WebNode) r10
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r11 = r1.mSnapInfo
            float r11 = r11.webViewScale
            r1.mergeWebViewNodes(r2, r10, r3, r11)
            boolean r11 = r10.isRootView()
            if (r11 == 0) goto L_0x00be
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = r10.getId()
            r11.append(r12)
            int r12 = r24.hashCode()
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r4.add(r11)
        L_0x00be:
            goto L_0x008b
        L_0x00bf:
            goto L_0x00eb
        L_0x00c0:
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo$Status r8 = r0.getStatus()
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo$Status r9 = com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo.Status.FAILURE
            if (r8 != r9) goto L_0x00bf
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r8 = r1.mSnapInfo
            java.util.List r9 = r0.getAlertInfos()
            r8.alertInfos = r9
            goto L_0x00eb
        L_0x00d1:
            com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$AlertRunnable r8 = r1.mAlertRunnable
            if (r8 != 0) goto L_0x00e0
            com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$AlertRunnable r8 = new com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$AlertRunnable
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r9 = r1.mSnapInfo
            java.lang.String r9 = r9.webViewUrl
            r8.<init>(r9)
            r1.mAlertRunnable = r8
        L_0x00e0:
            com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher r8 = com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher.getInstance()
            com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$AlertRunnable r9 = r1.mAlertRunnable
            r10 = 5000(0x1388, double:2.4703E-320)
            r8.postDelayed(r9, r10)
        L_0x00eb:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r7 = r0
            int r0 = r24.hashCode()
            java.lang.String r8 = "hashCode"
            r7.put((java.lang.String) r8, (int) r0)
            int r0 = r24.getId()
            java.lang.String r8 = "id"
            r7.put((java.lang.String) r8, (int) r0)
            android.view.ViewParent r0 = r24.getParent()
            int r0 = com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil.getChildIndex(r0, r3)
            java.lang.String r8 = "index"
            r7.put((java.lang.String) r8, (int) r0)
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.ViewUtil.instanceOfWebView(r24)
            java.lang.String r8 = "element_level"
            if (r0 == 0) goto L_0x011c
            r7.put((java.lang.String) r8, (int) r5)
            goto L_0x0126
        L_0x011c:
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r0 = r1.mSnapInfo
            int r9 = r0.elementLevel
            int r9 = r9 + r6
            r0.elementLevel = r9
            r7.put((java.lang.String) r8, (int) r9)
        L_0x0126:
            java.lang.String r0 = com.sensorsdata.analytics.android.sdk.util.ViewUtil.getElementSelector(r24)
            java.lang.String r8 = "element_selector"
            r7.put((java.lang.String) r8, (java.lang.Object) r0)
            com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r0 = r1.mSnapInfo
            org.json.JSONObject r8 = com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil.getScreenNameAndTitle(r3, r0)
            if (r8 == 0) goto L_0x015b
            java.lang.String r0 = "$screen_name"
            java.lang.String r0 = r8.optString(r0)
            java.lang.String r9 = "$title"
            java.lang.String r9 = r8.optString(r9)
            boolean r10 = android.text.TextUtils.isEmpty(r0)
            if (r10 != 0) goto L_0x014f
            java.lang.String r10 = "screen_name"
            r7.put((java.lang.String) r10, (java.lang.Object) r0)
        L_0x014f:
            boolean r10 = android.text.TextUtils.isEmpty(r9)
            if (r10 != 0) goto L_0x015b
            java.lang.String r10 = "title"
            r7.put((java.lang.String) r10, (java.lang.Object) r9)
        L_0x015b:
            r9 = r25
            com.sensorsdata.analytics.android.sdk.visual.model.ViewNode r10 = com.sensorsdata.analytics.android.sdk.util.ViewUtil.getViewNode(r3, r9, r6)
            if (r10 == 0) goto L_0x01ab
            java.lang.String r0 = r10.getViewPath()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0176
            java.lang.String r0 = r10.getViewPath()
            java.lang.String r11 = "element_path"
            r7.put((java.lang.String) r11, (java.lang.Object) r0)
        L_0x0176:
            java.lang.String r0 = r10.getViewPosition()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0189
            java.lang.String r0 = r10.getViewPosition()
            java.lang.String r11 = "element_position"
            r7.put((java.lang.String) r11, (java.lang.Object) r0)
        L_0x0189:
            java.lang.String r0 = r10.getViewContent()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01a2
            boolean r0 = com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil.isSupportElementContent(r24)
            if (r0 == 0) goto L_0x01a2
            java.lang.String r0 = r10.getViewContent()
            java.lang.String r11 = "element_content"
            r7.put((java.lang.String) r11, (java.lang.Object) r0)
        L_0x01a2:
            boolean r0 = r10.isListView()
            java.lang.String r11 = "is_list_view"
            r7.put((java.lang.String) r11, (boolean) r0)
        L_0x01ab:
            java.lang.String r0 = r1.getResName(r3)
            java.lang.String r11 = "sa_id_name"
            r7.put((java.lang.String) r11, (java.lang.Object) r0)
            int r0 = com.sensorsdata.analytics.android.sdk.R.id.sensors_analytics_tag_view_id     // Catch:{ Exception -> 0x01c7 }
            java.lang.Object r0 = r3.getTag(r0)     // Catch:{ Exception -> 0x01c7 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x01c7 }
            boolean r12 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x01c7 }
            if (r12 != 0) goto L_0x01c6
            r7.put((java.lang.String) r11, (java.lang.Object) r0)     // Catch:{ Exception -> 0x01c7 }
        L_0x01c6:
            goto L_0x01cb
        L_0x01c7:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x01cb:
            android.view.View r0 = r24.getRootView()
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.WindowHelper.isMainWindow(r0)
            java.lang.String r12 = "height"
            java.lang.String r13 = "width"
            java.lang.String r14 = "left"
            java.lang.String r15 = "top"
            if (r0 != 0) goto L_0x025f
            java.lang.Class r0 = r24.getClass()
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.WindowHelper.isDecorView(r0)
            if (r0 == 0) goto L_0x0210
            android.content.Context r0 = r24.getContext()
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            int r6 = r0.widthPixels
            int r11 = r0.heightPixels
            r18 = r0
            int r0 = r24.getTop()
            r7.put((java.lang.String) r15, (int) r0)
            int r0 = r24.getLeft()
            r7.put((java.lang.String) r14, (int) r0)
            r7.put((java.lang.String) r13, (int) r6)
            r7.put((java.lang.String) r12, (int) r11)
            goto L_0x027b
        L_0x0210:
            android.view.ViewParent r0 = r24.getParent()
            if (r0 == 0) goto L_0x0242
            java.lang.Class r6 = r0.getClass()
            boolean r6 = com.sensorsdata.analytics.android.sdk.util.WindowHelper.isDecorView(r6)
            if (r6 == 0) goto L_0x0242
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            r11 = 0
            r1.getVisibleRect(r3, r6, r11)
            int r11 = r6.top
            r7.put((java.lang.String) r15, (int) r11)
            int r11 = r6.left
            r7.put((java.lang.String) r14, (int) r11)
            int r11 = r6.width()
            r7.put((java.lang.String) r13, (int) r11)
            int r11 = r6.height()
            r7.put((java.lang.String) r12, (int) r11)
            goto L_0x025e
        L_0x0242:
            int r6 = r24.getTop()
            r7.put((java.lang.String) r15, (int) r6)
            int r6 = r24.getLeft()
            r7.put((java.lang.String) r14, (int) r6)
            int r6 = r24.getWidth()
            r7.put((java.lang.String) r13, (int) r6)
            int r6 = r24.getHeight()
            r7.put((java.lang.String) r12, (int) r6)
        L_0x025e:
            goto L_0x027b
        L_0x025f:
            int r0 = r24.getTop()
            r7.put((java.lang.String) r15, (int) r0)
            int r0 = r24.getLeft()
            r7.put((java.lang.String) r14, (int) r0)
            int r0 = r24.getWidth()
            r7.put((java.lang.String) r13, (int) r0)
            int r0 = r24.getHeight()
            r7.put((java.lang.String) r12, (int) r0)
        L_0x027b:
            int r0 = r24.getScrollX()
            boolean r6 = r3 instanceof android.widget.TextView
            if (r6 == 0) goto L_0x0296
            r6 = r3
            android.widget.TextView r6 = (android.widget.TextView) r6
            int r11 = android.os.Build.VERSION.SDK_INT
            r12 = 16
            if (r11 < r12) goto L_0x0296
            int r11 = r6.getMaxLines()
            r12 = 1
            if (r11 != r12) goto L_0x0296
            r0 = 0
            r6 = r0
            goto L_0x0297
        L_0x0296:
            r6 = r0
        L_0x0297:
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.ViewUtil.instanceOfX5WebView(r24)
            java.lang.String r11 = "scrollY"
            java.lang.String r12 = "scrollX"
            if (r0 == 0) goto L_0x02c8
            java.lang.String r0 = "getWebScrollX"
            r13 = 0
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x02c0 }
            java.lang.Object r0 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.callMethod(r3, r0, r14)     // Catch:{ Exception -> 0x02c2 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ Exception -> 0x02c2 }
            r7.put((java.lang.String) r12, (java.lang.Object) r0)     // Catch:{ Exception -> 0x02c2 }
            java.lang.String r0 = "getWebScrollY"
            r13 = 0
            java.lang.Object[] r12 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x02c0 }
            java.lang.Object r0 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.callMethod(r3, r0, r12)     // Catch:{ Exception -> 0x02c0 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ Exception -> 0x02c0 }
            r7.put((java.lang.String) r11, (java.lang.Object) r0)     // Catch:{ Exception -> 0x02c0 }
            goto L_0x02c7
        L_0x02c0:
            r0 = move-exception
            goto L_0x02c4
        L_0x02c2:
            r0 = move-exception
            r13 = 0
        L_0x02c4:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x02c7:
            goto L_0x02d3
        L_0x02c8:
            r13 = 0
            r7.put((java.lang.String) r12, (int) r6)
            int r0 = r24.getScrollY()
            r7.put((java.lang.String) r11, (int) r0)
        L_0x02d3:
            int r0 = com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil.getVisibility(r24)
            java.lang.String r11 = "visibility"
            r7.put((java.lang.String) r11, (int) r0)
            r0 = 0
            r11 = 0
            int r12 = android.os.Build.VERSION.SDK_INT
            r14 = 11
            if (r12 < r14) goto L_0x02ed
            float r0 = r24.getTranslationX()
            float r11 = r24.getTranslationY()
        L_0x02ed:
            double r14 = (double) r0
            java.lang.String r12 = "translationX"
            r7.put((java.lang.String) r12, (double) r14)
            double r14 = (double) r11
            java.lang.String r12 = "translationY"
            r7.put((java.lang.String) r12, (double) r14)
            org.json.JSONArray r12 = new org.json.JSONArray
            r12.<init>()
            java.lang.Class r14 = r24.getClass()
        L_0x0304:
            int r15 = android.os.Build.VERSION.SDK_INT
            r13 = 12
            if (r15 < r13) goto L_0x0313
            com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot$ClassNameCache r13 = r1.mClassnameCache
            java.lang.Object r13 = r13.get(r14)
            r12.put((java.lang.Object) r13)
        L_0x0313:
            java.lang.Class r14 = r14.getSuperclass()
            java.lang.Class<java.lang.Object> r13 = java.lang.Object.class
            if (r14 == r13) goto L_0x0320
            if (r14 != 0) goto L_0x031e
            goto L_0x0320
        L_0x031e:
            r13 = 0
            goto L_0x0304
        L_0x0320:
            java.lang.String r13 = "classes"
            r7.put((java.lang.String) r13, (java.lang.Object) r12)
            r1.addProperties(r7, r3)
            android.view.ViewGroup$LayoutParams r13 = r24.getLayoutParams()
            boolean r15 = r13 instanceof android.widget.RelativeLayout.LayoutParams
            if (r15 == 0) goto L_0x0364
            r15 = r13
            android.widget.RelativeLayout$LayoutParams r15 = (android.widget.RelativeLayout.LayoutParams) r15
            r16 = r0
            int[] r0 = r15.getRules()
            org.json.JSONArray r18 = new org.json.JSONArray
            r18.<init>()
            r19 = r18
            r18 = r5
            int r5 = r0.length
            r20 = r6
            r6 = 0
        L_0x0346:
            if (r6 >= r5) goto L_0x035a
            r17 = r5
            r5 = r0[r6]
            r21 = r0
            r0 = r19
            r0.put((int) r5)
            int r6 = r6 + 1
            r5 = r17
            r0 = r21
            goto L_0x0346
        L_0x035a:
            r21 = r0
            r0 = r19
            java.lang.String r5 = "layoutRules"
            r7.put((java.lang.String) r5, (java.lang.Object) r0)
            goto L_0x036a
        L_0x0364:
            r16 = r0
            r18 = r5
            r20 = r6
        L_0x036a:
            org.json.JSONArray r0 = new org.json.JSONArray
            r0.<init>()
            if (r4 == 0) goto L_0x038e
            int r5 = r4.size()
            if (r5 <= 0) goto L_0x038e
            java.util.Iterator r5 = r4.iterator()
        L_0x037b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x038b
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            r0.put((java.lang.Object) r6)
            goto L_0x037b
        L_0x038b:
            r19 = r4
            goto L_0x03b8
        L_0x038e:
            boolean r5 = r3 instanceof android.view.ViewGroup
            if (r5 == 0) goto L_0x03b6
            r5 = r3
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            int r6 = r5.getChildCount()
            r15 = 0
        L_0x039a:
            if (r15 >= r6) goto L_0x03b3
            android.view.View r17 = r5.getChildAt(r15)
            if (r17 == 0) goto L_0x03ac
            r19 = r4
            int r4 = r17.hashCode()
            r0.put((int) r4)
            goto L_0x03ae
        L_0x03ac:
            r19 = r4
        L_0x03ae:
            int r15 = r15 + 1
            r4 = r19
            goto L_0x039a
        L_0x03b3:
            r19 = r4
            goto L_0x03b8
        L_0x03b6:
            r19 = r4
        L_0x03b8:
            java.lang.String r4 = "subviews"
            r7.put((java.lang.String) r4, (java.lang.Object) r0)
            r2.put((java.lang.Object) r7)
            goto L_0x03c4
        L_0x03c2:
            r9 = r25
        L_0x03c4:
            boolean r0 = r3 instanceof android.view.ViewGroup
            if (r0 == 0) goto L_0x03de
            r0 = r3
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            int r4 = r0.getChildCount()
            r5 = 0
        L_0x03d0:
            if (r5 >= r4) goto L_0x03de
            android.view.View r6 = r0.getChildAt(r5)
            if (r6 == 0) goto L_0x03db
            r1.snapshotView(r2, r6, r5)
        L_0x03db:
            int r5 = r5 + 1
            goto L_0x03d0
        L_0x03de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot.snapshotView(org.json.JSONArray, android.view.View, int):void");
    }

    private void addProperties(JSONObject j, View v) {
        Caller caller;
        Object value;
        j.put("importantForAccessibility", true);
        Class<?> viewClass = v.getClass();
        for (PropertyDescription desc : this.mProperties) {
            if (!(!desc.targetClass.isAssignableFrom(viewClass) || (caller = desc.accessor) == null || (value = caller.applyMethod(v)) == null)) {
                if (value instanceof Number) {
                    j.put(desc.name, (Object) (Number) value);
                } else if (value instanceof Boolean) {
                    boolean clickable = ((Boolean) value).booleanValue();
                    if ("clickable".equals(desc.name)) {
                        if (VisualUtil.isSupportClick(v)) {
                            clickable = true;
                        } else if (VisualUtil.isForbiddenClick(v)) {
                            clickable = false;
                        }
                    }
                    j.put(desc.name, clickable);
                } else if (value instanceof ColorStateList) {
                    j.put(desc.name, (Object) Integer.valueOf(((ColorStateList) value).getDefaultColor()));
                } else if (value instanceof Drawable) {
                    Drawable drawable = (Drawable) value;
                    Rect bounds = drawable.getBounds();
                    JSONObject json = new JSONObject();
                    JSONArray classesArray = new JSONArray();
                    Class klass = drawable.getClass();
                    while (klass != Object.class && klass != null) {
                        classesArray.put((Object) SnapCache.getInstance().getCanonicalName(klass));
                        klass = klass.getSuperclass();
                    }
                    json.put("classes", (Object) classesArray);
                    JSONObject jsonDimensions = new JSONObject();
                    jsonDimensions.put("left", bounds.left);
                    jsonDimensions.put("right", bounds.right);
                    jsonDimensions.put("top", bounds.top);
                    jsonDimensions.put("bottom", bounds.bottom);
                    json.put("dimensions", (Object) jsonDimensions);
                    if (drawable instanceof ColorDrawable) {
                        ColorDrawable colorDrawable = (ColorDrawable) drawable;
                        if (Build.VERSION.SDK_INT >= 11) {
                            json.put(TypedValues.Custom.S_COLOR, colorDrawable.getColor());
                        }
                    }
                    j.put(desc.name, (Object) json);
                } else {
                    j.put(desc.name, (Object) value.toString());
                }
            }
        }
    }

    private boolean isSnapShotUpdated(String newImageHash, StringBuilder lastImageHash) {
        boolean isUpdated = false;
        if (!(newImageHash == null || lastImageHash == null)) {
            isUpdated = newImageHash.equals(lastImageHash.toString());
        }
        boolean isUpdated2 = !isUpdated || WebNodesManager.getInstance().hasH5AlertInfo();
        if (lastImageHash != null) {
            lastImageHash.delete(0, lastImageHash.length()).append(newImageHash);
        }
        return isUpdated2;
    }

    private String getResName(View view) {
        int viewId = view.getId();
        if (-1 == viewId) {
            return null;
        }
        return this.mResourceIds.nameForId(viewId);
    }

    @SuppressLint({"NewApi"})
    public static class ClassNameCache extends LruCache<Class<?>, String> {
        public ClassNameCache(int maxSize) {
            super(maxSize);
        }

        /* access modifiers changed from: protected */
        public String create(Class<?> klass) {
            return klass.getCanonicalName();
        }
    }

    public static class RootViewFinder implements Callable<List<RootViewInfo>> {
        private final CachedBitmap mCachedBitmap = new CachedBitmap();
        private final int mClientDensity = Opcodes.IF_ICMPNE;
        private final List<RootViewInfo> mRootViews = new ArrayList();

        public List<RootViewInfo> call() {
            this.mRootViews.clear();
            try {
                Activity activity = AppStateManager.getInstance().getForegroundActivity();
                if (activity != null) {
                    JSONObject object = AopUtil.buildTitleAndScreenName(activity);
                    VisualUtil.mergeRnScreenNameAndTitle(object);
                    String screenName = object.optString(AopConstants.SCREEN_NAME);
                    String activityTitle = object.optString(AopConstants.TITLE);
                    View rootView = null;
                    Window window = activity.getWindow();
                    if (window != null && window.isActive()) {
                        rootView = window.getDecorView().getRootView();
                    }
                    if (rootView == null) {
                        return this.mRootViews;
                    }
                    RootViewInfo info = new RootViewInfo(screenName, activityTitle, rootView);
                    View[] views = WindowHelper.getSortedWindowViews();
                    Bitmap bitmap = null;
                    if (views != null && views.length > 0) {
                        bitmap = mergeViewLayers(views, info);
                        for (View view : views) {
                            if (view.getWindowVisibility() == 0 && view.getVisibility() == 0 && view.getWidth() != 0 && view.getHeight() != 0) {
                                if (!TextUtils.equals(WindowHelper.getWindowPrefix(view), WindowHelper.getMainWindowPrefix())) {
                                    if (!WindowHelper.isCustomWindow(view)) {
                                        RootViewInfo subInfo = new RootViewInfo(screenName, activityTitle, view.getRootView());
                                        scaleBitmap(subInfo, bitmap);
                                        this.mRootViews.add(subInfo);
                                    }
                                }
                            }
                        }
                    }
                    if (this.mRootViews.size() == 0) {
                        scaleBitmap(info, bitmap);
                        this.mRootViews.add(info);
                    }
                }
            } catch (Throwable e) {
                SALog.d(ViewSnapshot.TAG, "" + e);
            }
            return this.mRootViews;
        }

        /* access modifiers changed from: package-private */
        public Bitmap mergeViewLayers(View[] views, RootViewInfo info) {
            boolean skipOther;
            char c;
            int i;
            int i2;
            View view;
            View[] viewArr = views;
            RootViewInfo rootViewInfo = info;
            int width = rootViewInfo.rootView.getWidth();
            int height = rootViewInfo.rootView.getHeight();
            char c2 = 0;
            char c3 = 1;
            if (width == 0 || height == 0) {
                int[] screenSize = DeviceUtils.getDeviceSize(SensorsDataAPI.sharedInstance().getContext());
                width = screenSize[0];
                height = screenSize[1];
                if (width == 0 || height == 0) {
                    return null;
                }
            }
            Bitmap fullScreenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            SoftWareCanvas canvas = new SoftWareCanvas(fullScreenBitmap);
            int[] windowOffset = new int[2];
            boolean isDrawBackground = false;
            if (ViewUtil.getMainWindowCount(views) > 1) {
                skipOther = true;
            } else {
                skipOther = false;
            }
            WindowHelper.init();
            ViewUtil.invalidateLayerTypeView(views);
            int length = viewArr.length;
            int i3 = 0;
            while (i3 < length) {
                View view2 = viewArr[i3];
                if (view2.getVisibility() != 0 || view2.getWidth() == 0 || view2.getHeight() == 0 || !ViewUtil.isWindowNeedTraverse(view2, WindowHelper.getWindowPrefix(view2), skipOther)) {
                    c = c3;
                    View view3 = view2;
                    i2 = i3;
                    i = length;
                } else {
                    canvas.save();
                    if (!WindowHelper.isMainWindow(view2)) {
                        view2.getLocationOnScreen(windowOffset);
                        canvas.translate((float) windowOffset[c2], (float) windowOffset[c3]);
                        if (!WindowHelper.isDialogOrPopupWindow(view2) || isDrawBackground) {
                            view = view2;
                            i2 = i3;
                            i = length;
                        } else {
                            Paint paint = new Paint();
                            paint.setColor(-1610612736);
                            view = view2;
                            i2 = i3;
                            i = length;
                            canvas.drawRect(-((float) windowOffset[c2]), -((float) windowOffset[c3]), (float) canvas.getWidth(), (float) canvas.getHeight(), paint);
                            isDrawBackground = true;
                        }
                    } else {
                        view = view2;
                        i2 = i3;
                        i = length;
                    }
                    view.draw(canvas);
                    c = 1;
                    canvas.restoreToCount(1);
                }
                i3 = i2 + 1;
                viewArr = views;
                length = i;
                c3 = c;
                c2 = 0;
            }
            canvas.destroy();
            return fullScreenBitmap;
        }

        private void scaleBitmap(RootViewInfo info, Bitmap rawBitmap) {
            float scale = 1.0f;
            if (rawBitmap != null) {
                int rawDensity = rawBitmap.getDensity();
                if (rawDensity != 0) {
                    scale = 160.0f / ((float) rawDensity);
                }
                int rawWidth = rawBitmap.getWidth();
                int rawHeight = rawBitmap.getHeight();
                int destWidth = (int) (((double) (((float) rawBitmap.getWidth()) * scale)) + 0.5d);
                int destHeight = (int) (((double) (((float) rawBitmap.getHeight()) * scale)) + 0.5d);
                if (rawWidth > 0 && rawHeight > 0 && destWidth > 0 && destHeight > 0) {
                    this.mCachedBitmap.recreate(destWidth, destHeight, Opcodes.IF_ICMPNE, rawBitmap);
                }
            }
            info.scale = scale;
            info.screenshot = this.mCachedBitmap;
        }
    }

    public static class CachedBitmap {
        private Bitmap mCached = null;
        private String mImageHash = "";
        private final Paint mPaint = new Paint(2);

        public synchronized void recreate(int width, int height, int destDensity, Bitmap source) {
            byte[] debugInfoBytes;
            byte[] webNodesArray;
            Bitmap bitmap = this.mCached;
            if (!(bitmap != null && bitmap.getWidth() == width && this.mCached.getHeight() == height)) {
                try {
                    this.mCached = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                } catch (Throwable th) {
                    this.mCached = null;
                }
                Bitmap bitmap2 = this.mCached;
                if (bitmap2 != null) {
                    bitmap2.setDensity(destDensity);
                }
            }
            if (this.mCached != null) {
                new Canvas(this.mCached).drawBitmap(source, 0.0f, 0.0f, this.mPaint);
                try {
                    ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
                    this.mCached.compress(Bitmap.CompressFormat.PNG, 100, imageByte);
                    byte[] array = imageByte.toByteArray();
                    String msg = WebNodesManager.getInstance().getLastWebNodeMsg();
                    if (!TextUtils.isEmpty(msg) && (webNodesArray = msg.getBytes()) != null && webNodesArray.length > 0) {
                        array = concat(array, webNodesArray);
                    }
                    String debugInfo = VisualizedAutoTrackService.getInstance().getLastDebugInfo();
                    if (!TextUtils.isEmpty(debugInfo) && (debugInfoBytes = debugInfo.getBytes()) != null && debugInfoBytes.length > 0) {
                        array = concat(array, debugInfoBytes);
                    }
                    this.mImageHash = toHex(MessageDigest.getInstance("MD5").digest(array));
                } catch (Exception e) {
                    SALog.i(ViewSnapshot.TAG, "CachedBitmap.recreate;Create image_hash error=" + e);
                }
            }
            return;
        }

        private static byte[] concat(byte[] first, byte[] second) {
            byte[] result = new byte[(first.length + second.length)];
            System.arraycopy(first, 0, result, 0, first.length);
            System.arraycopy(second, 0, result, first.length, second.length);
            return result;
        }

        public synchronized void writeBitmapJSON(Bitmap.CompressFormat format, int quality, OutputStream out) {
            Bitmap bitmap = this.mCached;
            if (!(bitmap == null || bitmap.getWidth() == 0)) {
                if (this.mCached.getHeight() != 0) {
                    out.write(34);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    this.mCached.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.flush();
                    out.write(new String(Base64Coder.encode(stream.toByteArray())).getBytes());
                    out.write(34);
                }
            }
            out.write(BuildConfig.TRAVIS.getBytes());
        }

        /* access modifiers changed from: private */
        public String getImageHash() {
            return this.mImageHash;
        }

        private String toHex(byte[] ary) {
            String ret = "";
            for (int i = 0; i < ary.length; i++) {
                ret = (ret + "0123456789ABCDEF".charAt((ary[i] >> 4) & 15)) + "0123456789ABCDEF".charAt(ary[i] & 15);
            }
            return ret;
        }
    }

    public static class RootViewInfo {
        final String activityTitle;
        final View rootView;
        float scale = 1.0f;
        final String screenName;
        CachedBitmap screenshot = null;

        RootViewInfo(String screenName2, String activityTitle2, View rootView2) {
            this.screenName = screenName2;
            this.activityTitle = activityTitle2;
            this.rootView = rootView2;
        }
    }

    private void mergeWebViewNodes(JSONArray j, WebNode view, View webView, float webViewScale) {
        float webViewScale2;
        try {
            JSONObject jsonWebView = new JSONObject();
            jsonWebView.put("hashCode", (Object) view.getId() + webView.hashCode());
            int i = 0;
            jsonWebView.put(FirebaseAnalytics.Param.INDEX, 0);
            if (!TextUtils.isEmpty(view.get$element_selector())) {
                jsonWebView.put("element_selector", (Object) view.get$element_selector());
            }
            if (!TextUtils.isEmpty(view.get$element_content())) {
                jsonWebView.put("element_content", (Object) view.get$element_content());
            }
            SnapInfo snapInfo = this.mSnapInfo;
            int i2 = snapInfo.elementLevel + 1;
            snapInfo.elementLevel = i2;
            jsonWebView.put("element_level", i2);
            jsonWebView.put("h5_title", (Object) view.get$title());
            if (webViewScale == 0.0f) {
                webViewScale2 = view.getScale();
            } else {
                webViewScale2 = webViewScale;
            }
            try {
                jsonWebView.put("left", (double) (view.getLeft() * webViewScale2));
                jsonWebView.put("top", (double) (view.getTop() * webViewScale2));
                jsonWebView.put("width", (int) (view.getWidth() * webViewScale2));
                jsonWebView.put("height", (int) (view.getHeight() * webViewScale2));
                jsonWebView.put("scrollX", 0);
                jsonWebView.put("scrollY", 0);
                boolean insideWebView = view.getOriginTop() * webViewScale2 <= ((float) webView.getHeight()) && view.getOriginLeft() * webViewScale2 <= ((float) webView.getWidth());
                if (!view.isVisibility() || !insideWebView) {
                    i = 8;
                }
                jsonWebView.put("visibility", i);
                jsonWebView.put("url", (Object) view.get$url());
                jsonWebView.put("clickable", view.isEnable_click());
                jsonWebView.put("importantForAccessibility", true);
                jsonWebView.put("is_h5", true);
                jsonWebView.put("is_list_view", view.isIs_list_view());
                jsonWebView.put("element_path", (Object) view.get$element_path());
                jsonWebView.put("tag_name", (Object) view.getTagName());
                if (!TextUtils.isEmpty(view.get$element_position())) {
                    jsonWebView.put("element_position", (Object) view.get$element_position());
                }
                this.mSnapInfo.webLibVersion = view.getLib_version();
                jsonWebView.put("list_selector", (Object) view.getList_selector());
                JSONArray classesArray = new JSONArray();
                classesArray.put((Object) view.getTagName());
                Class<?> klass = webView.getClass();
                do {
                    classesArray.put((Object) SnapCache.getInstance().getCanonicalName(klass));
                    klass = klass.getSuperclass();
                    if (klass == Object.class) {
                        break;
                    }
                } while (klass != null);
                jsonWebView.put("classes", (Object) classesArray);
                List<String> list = view.getSubelements();
                JSONArray subviewsArray = new JSONArray();
                if (list != null && list.size() > 0) {
                    for (String id : list) {
                        subviewsArray.put((Object) id + webView.hashCode());
                    }
                }
                jsonWebView.put("subviews", (Object) subviewsArray);
                JSONArray jSONArray = j;
                try {
                    j.put((Object) jsonWebView);
                } catch (Exception e) {
                    e = e;
                }
            } catch (Exception e2) {
                e = e2;
                JSONArray jSONArray2 = j;
                SALog.printStackTrace(e);
            }
        } catch (Exception e3) {
            e = e3;
            JSONArray jSONArray3 = j;
            float f = webViewScale;
            SALog.printStackTrace(e);
        }
    }
}
