package com.sensorsdata.analytics.android.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.JavascriptInterface;
import com.sensorsdata.analytics.android.sdk.util.ReflectUtil;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class AppWebViewInterface {
    private static final String TAG = "SA.AppWebViewInterface";
    private final boolean enableVerify;
    private final Context mContext;
    private WeakReference<View> mWebView;
    private JSONObject properties;

    AppWebViewInterface(Context c, JSONObject p, boolean b) {
        this(c, p, b, (View) null);
    }

    AppWebViewInterface(Context c, JSONObject p, boolean b, View view) {
        this.mContext = c;
        this.properties = p;
        this.enableVerify = b;
        if (view != null) {
            this.mWebView = new WeakReference<>(view);
        }
    }

    @JavascriptInterface
    public String sensorsdata_call_app() {
        try {
            if (this.properties == null) {
                this.properties = new JSONObject();
            }
            this.properties.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "Android");
            String loginId = SensorsDataAPI.sharedInstance(this.mContext).getLoginId();
            if (!TextUtils.isEmpty(loginId)) {
                this.properties.put("distinct_id", (Object) loginId);
                this.properties.put("is_login", true);
            } else {
                this.properties.put("distinct_id", (Object) SensorsDataAPI.sharedInstance(this.mContext).getAnonymousId());
                this.properties.put("is_login", false);
            }
            return this.properties.toString();
        } catch (JSONException e) {
            SALog.i(TAG, e.getMessage());
            return null;
        }
    }

    @JavascriptInterface
    public void sensorsdata_track(String event) {
        try {
            SALog.i(TAG, "sensorsdata_track event = " + event);
            SensorsDataAPI.sharedInstance(this.mContext).trackEventFromH5(event, this.enableVerify);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @JavascriptInterface
    public boolean sensorsdata_verify(String event) {
        try {
            SALog.i(TAG, "sensorsdata_verify event = " + event);
            if (this.enableVerify) {
                return SensorsDataAPI.sharedInstance(this.mContext)._trackEventFromH5(event);
            }
            sensorsdata_track(event);
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    @JavascriptInterface
    public String sensorsdata_get_server_url() {
        SensorsDataAPI.sharedInstance();
        return AbstractSensorsDataAPI.getConfigOptions().isAutoTrackWebView ? SensorsDataAPI.sharedInstance().getServerUrl() : "";
    }

    @JavascriptInterface
    public boolean sensorsdata_visual_verify(String event) {
        try {
            SALog.i(TAG, "sensorsdata_visual_verify event = " + event);
            if (!this.enableVerify) {
                return true;
            }
            if (TextUtils.isEmpty(event)) {
                return false;
            }
            String serverUrl = new JSONObject(event).optString("server_url");
            if (!TextUtils.isEmpty(serverUrl)) {
                return new ServerUrl(serverUrl).check(new ServerUrl(SensorsDataAPI.sharedInstance().getServerUrl()));
            }
            return false;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @JavascriptInterface
    public void sensorsdata_js_call_app(String content) {
        try {
            if (this.mWebView != null) {
                SensorsDataAPI.sharedInstance().handleJsMessage(this.mWebView, content);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @JavascriptInterface
    public boolean sensorsdata_abtest_module() {
        try {
            if (ReflectUtil.callStaticMethod(ReflectUtil.getCurrentClass(new String[]{"com.sensorsdata.abtest.SensorsABTest"}), "shareInstance", new Object[0]) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    @JavascriptInterface
    public String sensorsdata_get_app_visual_config() {
        try {
            if (!AbstractSensorsDataAPI.getConfigOptions().isVisualizedPropertiesEnabled()) {
                return null;
            }
            VisualPropertiesManager.getInstance().getVisualPropertiesH5Helper().registerListeners();
            String visualCache = VisualPropertiesManager.getInstance().getVisualPropertiesCache().getVisualCache();
            if (!TextUtils.isEmpty(visualCache)) {
                return Base64.encodeToString(visualCache.getBytes(), 0);
            }
            return null;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
