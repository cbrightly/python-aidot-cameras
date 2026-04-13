package com.sensorsdata.analytics.android.sdk.visual;

import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.util.ReflectUtil;
import java.lang.ref.WeakReference;

public class WebViewVisualInterface {
    private static final String TAG = "SA.Visual.WebViewVisualInterface";
    /* access modifiers changed from: private */
    public WeakReference<View> mWebView;

    public WebViewVisualInterface(View webView) {
        this.mWebView = new WeakReference<>(webView);
    }

    @JavascriptInterface
    public void sensorsdata_hover_web_nodes(String msg) {
        try {
            WebNodesManager.getInstance().handlerMessage(msg);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    @JavascriptInterface
    public boolean sensorsdata_visualized_mode() {
        return VisualizedAutoTrackService.getInstance().isServiceRunning() || HeatMapService.getInstance().isServiceRunning();
    }

    @JavascriptInterface
    public void sensorsdata_visualized_alert_info(final String msg) {
        try {
            SALog.i(TAG, "sensorsdata_visualized_alert_info msg: " + msg);
            if (this.mWebView.get() != null) {
                ((View) this.mWebView.get()).post(new Runnable() {
                    public void run() {
                        String url = (String) ReflectUtil.callMethod(WebViewVisualInterface.this.mWebView.get(), "getUrl", new Object[0]);
                        if (!TextUtils.isEmpty(url)) {
                            SALog.i(WebViewVisualInterface.TAG, "sensorsdata_visualized_alert_info url: " + url);
                            WebNodesManager.getInstance().handlerFailure(url, msg);
                        }
                    }
                });
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
