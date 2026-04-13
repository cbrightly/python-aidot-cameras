package com.didichuxing.doraemonkit.util;

import android.content.Context;
import android.webkit.WebView;
import com.didichuxing.doraemonkit.config.GpsMockConfig;
import com.didichuxing.doraemonkit.model.LatLng;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.yanzhenjie.andserver.util.h;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WebUtil {
    public static void webViewLoadLocalHtml(final WebView view, String jsPath) {
        WebView webView = view;
        String str = "http://localhost";
        String assetFileToString = assetFileToString(view.getContext(), jsPath);
        String str2 = h.TEXT_HTML_VALUE;
        String str3 = "UTF-8";
        webView.loadDataWithBaseURL(str, assetFileToString, str2, str3, (String) null);
        SensorsDataAutoTrackHelper.loadDataWithBaseURL2(webView, str, assetFileToString, str2, str3, (String) null);
        view.postDelayed(new Runnable() {
            public void run() {
                LatLng latLng = GpsMockConfig.getMockLocation();
                if (latLng == null) {
                    latLng = new LatLng(0.0d, 0.0d);
                }
                String url = String.format("javascript:init(%s,%s)", new Object[]{Double.valueOf(latLng.latitude), Double.valueOf(latLng.longitude)});
                WebView webView = view;
                webView.loadUrl(url);
                SensorsDataAutoTrackHelper.loadUrl2(webView, url);
            }
        }, 1000);
    }

    public static String assetFileToString(Context c, String urlStr) {
        InputStream in = null;
        try {
            in = c.getAssets().open(urlStr);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return inputStreamToString(in);
    }

    private static String inputStreamToString(InputStream in) {
        String line;
        if (in == null) {
            return "";
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    continue;
                }
            } while (line != null);
            bufferedReader.close();
            in.close();
            String var4 = sb.toString();
            try {
                in.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }
            return var4;
        } catch (Exception var14) {
            var14.printStackTrace();
            try {
                in.close();
                return null;
            } catch (IOException var132) {
                var132.printStackTrace();
                return null;
            }
        } catch (Throwable th) {
            try {
                in.close();
            } catch (IOException var133) {
                var133.printStackTrace();
            }
            throw th;
        }
    }
}
