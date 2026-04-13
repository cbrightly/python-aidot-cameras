package com.sensorsdata.analytics.android.sdk.network;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static final int HTTP_307 = 307;

    HttpUtils() {
    }

    static boolean needRedirects(int responseCode) {
        return responseCode == 301 || responseCode == 302 || responseCode == 307;
    }

    static String getLocation(HttpURLConnection connection, String path) {
        if (connection == null || TextUtils.isEmpty(path)) {
            return null;
        }
        String location = connection.getHeaderField("Location");
        if (TextUtils.isEmpty(location)) {
            location = connection.getHeaderField(FirebaseAnalytics.Param.LOCATION);
        }
        if (TextUtils.isEmpty(location)) {
            return null;
        }
        if (location.startsWith(NetworkManager.MOCK_SCHEME_HTTP) || location.startsWith(NetworkManager.MOCK_SCHEME_HTTPS)) {
            return location;
        }
        URL originUrl = new URL(path);
        return originUrl.getProtocol() + "://" + originUrl.getHost() + location;
    }

    static String getRetString(InputStream is) {
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = reader2.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            }
            is.close();
            String buf = sb.toString();
            try {
                reader2.close();
            } catch (IOException e) {
                SALog.printStackTrace(e);
            }
            try {
                is.close();
            } catch (IOException e2) {
                SALog.printStackTrace(e2);
            }
            return buf;
        } catch (Exception e3) {
            SALog.printStackTrace(e3);
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                    SALog.printStackTrace(e4);
                }
            }
            if (is == null) {
                return "";
            }
            try {
                is.close();
                return "";
            } catch (IOException e5) {
                SALog.printStackTrace(e5);
                return "";
            }
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e6) {
                    SALog.printStackTrace(e6);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e7) {
                    SALog.printStackTrace(e7);
                }
            }
            throw th;
        }
    }
}
