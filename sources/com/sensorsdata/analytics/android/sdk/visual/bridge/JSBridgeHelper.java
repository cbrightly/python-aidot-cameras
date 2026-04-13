package com.sensorsdata.analytics.android.sdk.visual.bridge;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import com.google.firebase.messaging.Constants;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.listener.SAJSListener;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class JSBridgeHelper implements WebViewJavascriptBridge {
    private static final String CALLBACK_ID_FORMAT = "JAVA_CB_%s";
    private static final String CALL_TYPE_GET_VISUAL_PROPERTIES = "getJSVisualProperties";
    /* access modifiers changed from: private */
    public Map<String, OnBridgeCallback> mCallbacks = new HashMap();
    private SAJSListener mSAJSListener;

    public void addSAJSListener() {
        if (this.mSAJSListener == null) {
            this.mSAJSListener = new SAJSListener() {
                public void onReceiveJSMessage(WeakReference<View> weakReference, String message) {
                    OnBridgeCallback function;
                    JSONObject dataObj;
                    try {
                        JSONObject obj = new JSONObject(message);
                        if (TextUtils.equals(JSBridgeHelper.CALL_TYPE_GET_VISUAL_PROPERTIES, obj.optString("callType"))) {
                            String messageId = obj.optString(Constants.MessagePayloadKeys.MSGID_SERVER);
                            if (!TextUtils.isEmpty(messageId) && (function = (OnBridgeCallback) JSBridgeHelper.this.mCallbacks.remove(messageId)) != null && (dataObj = obj.optJSONObject("data")) != null) {
                                function.onCallBack(dataObj.toString());
                            }
                        }
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            };
            SensorsDataAPI.sharedInstance().addSAJSListener(this.mSAJSListener);
        }
    }

    public synchronized void sendToWeb(final View webView, final String methodName, Object data, OnBridgeCallback responseCallback) {
        try {
            if (!TextUtils.isEmpty(methodName)) {
                JSRequest request = new JSRequest();
                request.methodName = methodName;
                if (responseCallback != null) {
                    String messageId = String.format(CALLBACK_ID_FORMAT, new Object[]{Long.valueOf(SystemClock.currentThreadTimeMillis())});
                    this.mCallbacks.put(messageId, responseCallback);
                    request.messageId = messageId;
                }
                JSONObject object = null;
                if (data instanceof String) {
                    object = new JSONObject((String) data);
                } else if (data instanceof JSONObject) {
                    object = new JSONObject();
                    object.put(Constants.MessagePayloadKeys.MSGID_SERVER, (Object) request.messageId);
                    object.put("platform", (Object) "Android");
                    SensorsDataUtils.mergeJSONObject((JSONObject) data, object);
                }
                final JSONObject obj = object;
                if (obj != null) {
                    if (webView != null) {
                        webView.post(new Runnable() {
                            public void run() {
                                View view = webView;
                                JSBridgeHelper.invokeWebViewLoad(view, "loadUrl", new Object[]{"javascript:window.sensorsdata_app_call_js(" + ("'" + methodName + "','" + Base64.encodeToString(obj.toString().getBytes(), 0) + "'") + ")"}, new Class[]{String.class});
                            }
                        });
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return;
    }

    public void sendToWeb(View webView, String methodName, Object data) {
        sendToWeb(webView, methodName, data, (OnBridgeCallback) null);
    }

    /* access modifiers changed from: private */
    public static void invokeWebViewLoad(View webView, String methodName, Object[] params, Class[] paramTypes) {
        try {
            webView.getClass().getMethod(methodName, paramTypes).invoke(webView, params);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
