package com.sensorsdata.analytics.android.sdk.network;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.glassfish.grizzly.http.server.Constants;

public class RealRequest {
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;
    private static final String TAG = "SA.HttpRequest";
    private static String sRequestURL;

    RealRequest() {
    }

    /* access modifiers changed from: package-private */
    public RealResponse getData(String requestURL, Map<String, String> headerMap) {
        try {
            SALog.i(TAG, String.format("url:%s,\nmethod:GET", new Object[]{requestURL}));
            sRequestURL = requestURL;
            HttpURLConnection conn = getHttpURLConnection(requestURL, Constants.GET);
            if (headerMap != null) {
                setHeader(conn, headerMap);
            }
            conn.connect();
            return getRealResponse(conn);
        } catch (Exception e) {
            return getExceptionResponse(e);
        }
    }

    /* access modifiers changed from: package-private */
    public RealResponse postData(String requestURL, String body, String bodyType, Map<String, String> headerMap) {
        BufferedWriter writer = null;
        try {
            sRequestURL = requestURL;
            SALog.i(TAG, String.format("url:%s\nparams:%s\nmethod:POST", new Object[]{requestURL, body}));
            HttpURLConnection conn = getHttpURLConnection(requestURL, Constants.POST);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            if (!TextUtils.isEmpty(bodyType)) {
                conn.setRequestProperty("Content-Type", bodyType);
            }
            if (headerMap != null) {
                setHeader(conn, headerMap);
            }
            conn.connect();
            if (!TextUtils.isEmpty(body)) {
                writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.flush();
            }
            RealResponse realResponse = getRealResponse(conn);
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    SALog.printStackTrace(e);
                }
            }
            return realResponse;
        } catch (Exception e2) {
            RealResponse exceptionResponse = getExceptionResponse(e2);
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e3) {
                    SALog.printStackTrace(e3);
                }
            }
            return exceptionResponse;
        } catch (Throwable th) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e4) {
                    SALog.printStackTrace(e4);
                }
            }
            throw th;
        }
    }

    private HttpURLConnection getHttpURLConnection(String requestURL, String requestMethod) {
        SSLSocketFactory sSLSocketFactory;
        HttpURLConnection conn = (HttpURLConnection) new URL(requestURL).openConnection();
        conn.setRequestMethod(requestMethod);
        conn.setUseCaches(false);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        if (requestMethod.equals(Constants.POST)) {
            conn.setDoOutput(true);
        }
        SAConfigOptions configOptions = AbstractSensorsDataAPI.getConfigOptions();
        if (!(configOptions == null || (sSLSocketFactory = configOptions.mSSLSocketFactory) == null || !(conn instanceof HttpsURLConnection))) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sSLSocketFactory);
        }
        return conn;
    }

    private void setHeader(HttpURLConnection conn, Map<String, String> headerMap) {
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
    }

    private RealResponse getRealResponse(HttpURLConnection conn) {
        RealResponse response = new RealResponse();
        try {
            int responseCode = conn.getResponseCode();
            response.code = responseCode;
            if (HttpUtils.needRedirects(responseCode)) {
                response.location = HttpUtils.getLocation(conn, sRequestURL);
            }
            response.contentLength = (long) conn.getContentLength();
            if (response.code < 400) {
                response.result = HttpUtils.getRetString(conn.getInputStream());
            } else {
                response.errorMsg = HttpUtils.getRetString(conn.getErrorStream());
            }
            conn.disconnect();
            SALog.i(TAG, response.toString());
            return response;
        } catch (IOException e) {
            RealResponse exceptionResponse = getExceptionResponse(e);
            if (conn != null) {
                conn.disconnect();
            }
            return exceptionResponse;
        } catch (Throwable th) {
            if (conn != null) {
                conn.disconnect();
            }
            throw th;
        }
    }

    private RealResponse getExceptionResponse(Exception e) {
        RealResponse response = new RealResponse();
        response.exception = e;
        response.errorMsg = e.getMessage();
        SALog.i(TAG, response.toString());
        return response;
    }
}
