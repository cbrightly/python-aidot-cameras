package com.sensorsdata.analytics.android.sdk.visual.property;

import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.listener.SAEventListener;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import com.sensorsdata.analytics.android.sdk.visual.ViewTreeStatusObservable;
import com.sensorsdata.analytics.android.sdk.visual.bridge.JSBridgeHelper;
import com.sensorsdata.analytics.android.sdk.visual.bridge.OnBridgeCallback;
import com.sensorsdata.analytics.android.sdk.visual.bridge.WebViewJavascriptBridge;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class VisualPropertiesH5Helper implements WebViewJavascriptBridge {
    private JSBridgeHelper mJSBridgeHelper = new JSBridgeHelper();
    private SAEventListener mSAEventListener;
    /* access modifiers changed from: private */
    public SparseArray<JSONArray> mSparseArray = new SparseArray<>();

    /* access modifiers changed from: package-private */
    public void mergeJSVisualProperties(final JSONObject srcObject, HashSet<String> hashSet, String eventName) {
        View view;
        if (hashSet != null) {
            try {
                if (hashSet.size() != 0) {
                    Iterator<String> entries = hashSet.iterator();
                    final CountDownLatch latch = new CountDownLatch(hashSet.size());
                    while (entries.hasNext()) {
                        ViewNode viewNode = ViewTreeStatusObservable.getInstance().getViewNode(entries.next());
                        if (!(viewNode == null || viewNode.getView() == null || (view = (View) viewNode.getView().get()) == null)) {
                            getJSVisualProperties(view, viewNode.getViewPath(), eventName, new OnBridgeCallback() {
                                public void onCallBack(String data) {
                                    try {
                                        JSONObject obj = new JSONObject(data);
                                        Iterator<String> iterator = obj.keys();
                                        while (iterator.hasNext()) {
                                            String key = iterator.next();
                                            String value = obj.optString(key);
                                            if (!TextUtils.isEmpty(key)) {
                                                srcObject.put(key, (Object) value);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        SALog.printStackTrace(e);
                                    } catch (Throwable th) {
                                        latch.countDown();
                                        throw th;
                                    }
                                    latch.countDown();
                                }
                            });
                        }
                    }
                    try {
                        latch.await(500, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        SALog.printStackTrace(e);
                    }
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
            }
        }
    }

    private void getJSVisualProperties(View webView, String elementPath, String eventName, OnBridgeCallback onBridgeCallback) {
        try {
            JSONArray array = VisualPropertiesManager.getInstance().getVisualPropertiesCache().getH5JsonArrayFromCache(eventName, elementPath);
            if (array != null) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("sensorsdata_js_visual_properties", (Object) array);
                } catch (JSONException e) {
                    SALog.printStackTrace(e);
                }
                sendToWeb(webView, "getJSVisualProperties", obj, onBridgeCallback);
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }

    public void registerListeners() {
        try {
            this.mJSBridgeHelper.addSAJSListener();
            addSAEventListener();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void addSAEventListener() {
        if (this.mSAEventListener == null) {
            this.mSAEventListener = new SAEventListener() {
                public void trackEvent(JSONObject jsonObject) {
                    JSONObject propertiesObj;
                    try {
                        if (TextUtils.equals(AopConstants.WEB_CLICK_EVENT_NAME, jsonObject.optString(NotificationCompat.CATEGORY_EVENT)) && (propertiesObj = jsonObject.optJSONObject("properties")) != null) {
                            if (propertiesObj.has("sensorsdata_web_visual_eventName")) {
                                VisualPropertiesH5Helper.this.mSparseArray.put(jsonObject.hashCode(), propertiesObj.optJSONArray("sensorsdata_web_visual_eventName"));
                                propertiesObj.remove("sensorsdata_web_visual_eventName");
                            }
                            String base64Message = propertiesObj.optString("sensorsdata_app_visual_properties");
                            propertiesObj.remove("sensorsdata_app_visual_properties");
                            if (TextUtils.isEmpty(base64Message)) {
                                return;
                            }
                            if (AbstractSensorsDataAPI.getConfigOptions().isVisualizedPropertiesEnabled()) {
                                String appVisualProperties = Base64Coder.decodeString(base64Message);
                                if (!TextUtils.isEmpty(appVisualProperties)) {
                                    try {
                                        JSONArray array = new JSONArray(appVisualProperties);
                                        if (array.length() > 0) {
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject obj = array.getJSONObject(i);
                                                VisualConfig.VisualProperty visualProperty = new VisualConfig.VisualProperty();
                                                visualProperty.elementPath = obj.optString("element_path");
                                                visualProperty.elementPosition = obj.optString("element_position");
                                                visualProperty.screenName = obj.optString(FirebaseAnalytics.Param.SCREEN_NAME);
                                                visualProperty.name = obj.optString("name");
                                                visualProperty.regular = obj.optString("regular");
                                                visualProperty.isH5 = obj.optBoolean("h5");
                                                visualProperty.type = obj.optString(IjkMediaMeta.IJKM_KEY_TYPE);
                                                visualProperty.webViewElementPath = obj.optString("webview_element_path");
                                                VisualPropertiesManager.getInstance().mergeAppVisualProperty(visualProperty, (VisualConfig.VisualEvent) null, propertiesObj, (ViewNode) null);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        SALog.printStackTrace(e);
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                    }
                }

                public void login() {
                }

                public void logout() {
                }

                public void identify() {
                }

                public void resetAnonymousId() {
                }
            };
            SensorsDataAPI.sharedInstance().addEventListener(this.mSAEventListener);
        }
    }

    public JSONArray getEventName(int hashCode) {
        try {
            return this.mSparseArray.get(hashCode);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public void clearCache(int hashCode) {
        try {
            this.mSparseArray.remove(hashCode);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void sendToWeb(View webView, String methodName, Object data, OnBridgeCallback responseCallback) {
        this.mJSBridgeHelper.sendToWeb(webView, methodName, data, responseCallback);
    }

    public void sendToWeb(View webView, String methodName, Object data) {
        this.mJSBridgeHelper.sendToWeb(webView, methodName, data);
    }

    private static String Base642string(String s) {
        return new String(Base64.decode(s.getBytes(), 0));
    }
}
