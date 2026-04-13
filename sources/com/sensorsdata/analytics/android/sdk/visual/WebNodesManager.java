package com.sensorsdata.analytics.android.sdk.visual;

import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.visual.model.WebNode;
import com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo;
import com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebNodesManager {
    private static final String CALL_TYPE_PAGE_INFO = "page_info";
    private static final String CALL_TYPE_VISUALIZED_TRACK = "visualized_track";
    private static final int LRU_CACHE_MAX_SIZE = 10;
    private static final String TAG = "SA.Visual.WebNodesManager";
    private static volatile WebNodesManager mSingleton = null;
    private static LruCache<String, WebNodeInfo> sPageInfoCache;
    private static LruCache<String, WebNodeInfo> sWebNodesCache;
    private boolean mHasH5AlertInfo;
    private boolean mHasWebView;
    private String mLastWebNodeMsg = null;
    private String mWebViewUrl;

    private WebNodesManager() {
    }

    public static WebNodesManager getInstance() {
        if (mSingleton == null) {
            synchronized (WebNodesManager.class) {
                if (mSingleton == null) {
                    mSingleton = new WebNodesManager();
                }
            }
        }
        return mSingleton;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handlerMessage(java.lang.String r7) {
        /*
            r6 = this;
            com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher r0 = com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher.getInstance()
            r0.removeCallbacksAndMessages()
            com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService r0 = com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService.getInstance()
            boolean r0 = r0.isServiceRunning()
            if (r0 != 0) goto L_0x001c
            com.sensorsdata.analytics.android.sdk.visual.HeatMapService r0 = com.sensorsdata.analytics.android.sdk.visual.HeatMapService.getInstance()
            boolean r0 = r0.isServiceRunning()
            if (r0 != 0) goto L_0x001c
            return
        L_0x001c:
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 == 0) goto L_0x0023
            return
        L_0x0023:
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r6.mLastWebNodeMsg = r0
            r0 = 0
            r6.mHasH5AlertInfo = r0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r1.<init>((java.lang.String) r7)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            java.lang.String r2 = "callType"
            java.lang.String r2 = r1.optString(r2)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r3 = -1
            int r4 = r2.hashCode()     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            switch(r4) {
                case 817885468: goto L_0x004f;
                case 883555422: goto L_0x0044;
                default: goto L_0x0043;
            }     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
        L_0x0043:
            goto L_0x0059
        L_0x0044:
            java.lang.String r0 = "page_info"
            boolean r0 = r2.equals(r0)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r0 == 0) goto L_0x0043
            r0 = 1
            goto L_0x005a
        L_0x004f:
            java.lang.String r4 = "visualized_track"
            boolean r4 = r2.equals(r4)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r4 == 0) goto L_0x0043
            goto L_0x005a
        L_0x0059:
            r0 = r3
        L_0x005a:
            r3 = 10
            r4 = 12
            switch(r0) {
                case 0: goto L_0x0087;
                case 1: goto L_0x0062;
                default: goto L_0x0061;
            }     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
        L_0x0061:
            goto L_0x00bf
        L_0x0062:
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo r0 = r6.parsePageInfo(r7)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r0 == 0) goto L_0x00bf
            java.lang.String r5 = r0.getUrl()     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r6.mWebViewUrl = r5     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r5 < r4) goto L_0x00bf
            android.util.LruCache<java.lang.String, com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo> r4 = sPageInfoCache     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r4 != 0) goto L_0x007d
            android.util.LruCache r4 = new android.util.LruCache     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r4.<init>(r3)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            sPageInfoCache = r4     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
        L_0x007d:
            android.util.LruCache<java.lang.String, com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo> r3 = sPageInfoCache     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            java.lang.String r4 = r0.getUrl()     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r3.put(r4, r0)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            goto L_0x00bf
        L_0x0087:
            java.util.List r0 = r6.parseResult(r7)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r0 == 0) goto L_0x00bf
            int r5 = r0.size()     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r5 <= 0) goto L_0x00bf
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r5 < r4) goto L_0x00bf
            android.util.LruCache<java.lang.String, com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo> r4 = sWebNodesCache     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r4 != 0) goto L_0x00a2
            android.util.LruCache r4 = new android.util.LruCache     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r4.<init>(r3)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            sWebNodesCache = r4     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
        L_0x00a2:
            java.lang.String r3 = r6.mWebViewUrl     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            if (r3 != 0) goto L_0x00bf
            android.util.LruCache<java.lang.String, com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo> r3 = sWebNodesCache     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            java.lang.String r4 = r6.mWebViewUrl     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo r5 = com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo.createWebNodesInfo(r0)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x00bb, Exception -> 0x00b6 }
            goto L_0x00bf
        L_0x00b6:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
            goto L_0x00c0
        L_0x00bb:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x00bf:
        L_0x00c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.visual.WebNodesManager.handlerMessage(java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public void handlerFailure(String webViewUrl, String message) {
        try {
            Dispatcher.getInstance().removeCallbacksAndMessages();
            if ((VisualizedAutoTrackService.getInstance().isServiceRunning() || HeatMapService.getInstance().isServiceRunning()) && !TextUtils.isEmpty(message)) {
                SALog.i(TAG, "handlerFailure url " + webViewUrl + ",msg: " + message);
                this.mHasH5AlertInfo = true;
                this.mLastWebNodeMsg = String.valueOf(System.currentTimeMillis());
                List<WebNodeInfo.AlertInfo> list = parseAlertResult(message);
                if (list != null && list.size() > 0 && Build.VERSION.SDK_INT >= 12) {
                    if (sWebNodesCache == null) {
                        sWebNodesCache = new LruCache<>(10);
                    }
                    sWebNodesCache.put(webViewUrl, WebNodeInfo.createWebAlertInfo(list));
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private List<WebNode> parseResult(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return null;
        }
        List<WebNode> list = new ArrayList<>();
        Map<String, WebNodeRect> hashMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(msg);
            JSONArray data = jsonObject.optJSONArray("data");
            JSONArray extra = jsonObject.optJSONArray("extra_elements");
            if (data != null) {
                findWebNodes(data, list, hashMap);
            }
            if (extra != null) {
                findWebNodes(extra, list, hashMap);
            }
            if (!hashMap.isEmpty()) {
                modifyWebNodes(list, hashMap);
            }
            try {
                Collections.sort(list, new Comparator<WebNode>() {
                    public int compare(WebNode o1, WebNode o2) {
                        return o1.getLevel() - o2.getLevel();
                    }
                });
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } catch (JSONException e2) {
            SALog.printStackTrace(e2);
        } catch (Exception e3) {
            SALog.printStackTrace(e3);
        }
        return list;
    }

    private WebNodeInfo parsePageInfo(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return null;
        }
        try {
            JSONObject data = new JSONObject(msg).getJSONObject("data");
            return WebNodeInfo.createPageInfo(data.optString(AopConstants.TITLE), data.optString("$url"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<WebNodeInfo.AlertInfo> parseAlertResult(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return null;
        }
        List<WebNodeInfo.AlertInfo> list = null;
        try {
            JSONArray array = new JSONObject(msg).getJSONArray("data");
            if (array != null && array.length() > 0) {
                list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    if (object != null) {
                        list.add(new WebNodeInfo.AlertInfo(object.optString("title"), object.optString("message"), object.optString("link_text"), object.optString("link_url")));
                    }
                }
            }
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
        return list;
    }

    public static class WebNodeRect {
        public float left;
        public float top;

        public WebNodeRect(float top2, float left2) {
            this.top = top2;
            this.left = left2;
        }
    }

    private void modifyWebNodes(List<WebNode> webNodeList, Map<String, WebNodeRect> hashMap) {
        if (webNodeList != null && webNodeList.size() != 0) {
            synchronized (this) {
                for (WebNode webNode : webNodeList) {
                    webNode.setOriginLeft(webNode.getLeft());
                    webNode.setOriginTop(webNode.getTop());
                    if (!hashMap.containsKey(webNode.getId())) {
                        webNode.setRootView(true);
                        webNode.setTop(webNode.getTop() + webNode.getScrollY());
                        webNode.setLeft(webNode.getLeft() + webNode.getScrollX());
                    } else {
                        WebNodeRect rect = hashMap.get(webNode.getId());
                        if (rect != null) {
                            webNode.setTop(webNode.getTop() - rect.top);
                            webNode.setLeft(webNode.getLeft() - rect.left);
                        }
                    }
                }
            }
        }
    }

    private void findWebNodes(JSONArray array, List<WebNode> list, Map<String, WebNodeRect> hashMap) {
        if (array != null) {
            try {
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        WebNode webNode = new WebNode();
                        webNode.setId(object.optString("id"));
                        webNode.set$element_content(object.optString(AopConstants.ELEMENT_CONTENT));
                        webNode.set$element_selector(object.optString(AopConstants.ELEMENT_SELECTOR));
                        webNode.setTagName(object.optString("tagName"));
                        webNode.setTop((float) object.optDouble("top"));
                        webNode.setLeft((float) object.optDouble("left"));
                        webNode.setScrollX((float) object.optDouble("scrollX"));
                        webNode.setScrollY((float) object.optDouble("scrollY"));
                        webNode.setWidth((float) object.optDouble("width"));
                        webNode.setHeight((float) object.optDouble("height"));
                        webNode.setScale((float) object.optDouble("scale"));
                        webNode.setVisibility(object.optBoolean("visibility"));
                        webNode.set$url(object.optString("$url"));
                        webNode.setzIndex(object.optInt("zIndex"));
                        webNode.set$title(object.optString(AopConstants.TITLE));
                        webNode.setLevel(object.optInt(FirebaseAnalytics.Param.LEVEL));
                        webNode.set$element_path(object.optString(AopConstants.ELEMENT_PATH));
                        webNode.set$element_position(object.optString(AopConstants.ELEMENT_POSITION));
                        webNode.setList_selector(object.optString("list_selector"));
                        webNode.setLib_version(object.optString("lib_version"));
                        webNode.setEnable_click(object.optBoolean("enable_click", true));
                        webNode.setIs_list_view(object.optBoolean("is_list_view"));
                        JSONArray subElementsArray = object.optJSONArray("subelements");
                        List<String> subViewIds = new ArrayList<>();
                        if (subElementsArray != null && subElementsArray.length() > 0) {
                            for (int j = 0; j < subElementsArray.length(); j++) {
                                String subElementsId = subElementsArray.optString(j);
                                if (!TextUtils.isEmpty(subElementsId)) {
                                    subViewIds.add(subElementsId);
                                    if (!hashMap.containsKey(subElementsId)) {
                                        hashMap.put(subElementsId, new WebNodeRect(webNode.getTop(), webNode.getLeft()));
                                    }
                                }
                            }
                        }
                        if (subViewIds.size() > 0) {
                            webNode.setSubelements(subViewIds);
                        }
                        list.add(webNode);
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public WebNodeInfo getWebNodes(String webViewUrl) {
        if ((!VisualizedAutoTrackService.getInstance().isServiceRunning() && !HeatMapService.getInstance().isServiceRunning()) || Build.VERSION.SDK_INT < 12) {
            return null;
        }
        if (sWebNodesCache == null) {
            sWebNodesCache = new LruCache<>(10);
        }
        return sWebNodesCache.get(webViewUrl);
    }

    /* access modifiers changed from: package-private */
    public WebNodeInfo getWebPageInfo(String webViewUrl) {
        if ((!VisualizedAutoTrackService.getInstance().isServiceRunning() && !HeatMapService.getInstance().isServiceRunning()) || Build.VERSION.SDK_INT < 12) {
            return null;
        }
        if (sPageInfoCache == null) {
            sPageInfoCache = new LruCache<>(10);
        }
        return sPageInfoCache.get(webViewUrl);
    }

    /* access modifiers changed from: package-private */
    public String getLastWebNodeMsg() {
        return this.mLastWebNodeMsg;
    }

    /* access modifiers changed from: package-private */
    public boolean hasH5AlertInfo() {
        return this.mHasH5AlertInfo;
    }

    public void clear() {
        this.mLastWebNodeMsg = null;
        this.mHasH5AlertInfo = false;
    }

    /* access modifiers changed from: package-private */
    public void setHasWebView(boolean hasWebView) {
        this.mHasWebView = hasWebView;
    }

    /* access modifiers changed from: package-private */
    public boolean hasWebView() {
        return this.mHasWebView;
    }
}
