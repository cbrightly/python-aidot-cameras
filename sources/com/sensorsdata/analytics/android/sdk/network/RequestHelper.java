package com.sensorsdata.analytics.android.sdk.network;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class RequestHelper {
    /* access modifiers changed from: private */
    public boolean isRedirected;

    /* renamed from: com.sensorsdata.analytics.android.sdk.network.RequestHelper$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$sensorsdata$analytics$android$sdk$network$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$com$sensorsdata$analytics$android$sdk$network$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sensorsdata$analytics$android$sdk$network$HttpMethod[HttpMethod.POST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private RequestHelper(HttpMethod method, String url, Map<String, String> paramsMap, Map<String, String> headerMap, int retryCount, HttpCallback callBack) {
        this.isRedirected = false;
        switch (AnonymousClass3.$SwitchMap$com$sensorsdata$analytics$android$sdk$network$HttpMethod[method.ordinal()]) {
            case 1:
                urlHttpGet(url, paramsMap, headerMap, retryCount, callBack);
                return;
            case 2:
                urlHttpPost(url, paramsMap, "", headerMap, retryCount, callBack);
                return;
            default:
                return;
        }
    }

    private RequestHelper(String url, String jsonData, Map<String, String> headerMap, int retryCount, HttpCallback callBack) {
        this.isRedirected = false;
        urlHttpPost(url, (Map<String, String>) null, jsonData, headerMap, retryCount, callBack);
    }

    /* access modifiers changed from: private */
    public void urlHttpGet(String url, Map<String, String> paramsMap, Map<String, String> headerMap, int retryCount, HttpCallback callBack) {
        final String str = url;
        final Map<String, String> map = paramsMap;
        final Map<String, String> map2 = headerMap;
        final HttpCallback httpCallback = callBack;
        final int i = retryCount;
        final int i2 = retryCount - 1;
        HttpTaskManager.execute(new Runnable() {
            public void run() {
                RealResponse response = new RealRequest().getData(RequestHelper.this.getUrl(str, map), map2);
                int i = response.code;
                if (i == 200 || i == 204) {
                    HttpCallback httpCallback = httpCallback;
                    if (httpCallback != null) {
                        httpCallback.onSuccess(response);
                    }
                } else if (RequestHelper.this.isRedirected || !HttpUtils.needRedirects(response.code)) {
                    int i2 = i2;
                    if (i2 != 0) {
                        RequestHelper.this.urlHttpGet(str, map, map2, i2, httpCallback);
                        return;
                    }
                    HttpCallback httpCallback2 = httpCallback;
                    if (httpCallback2 != null) {
                        httpCallback2.onError(response);
                    }
                } else {
                    boolean unused = RequestHelper.this.isRedirected = true;
                    RequestHelper.this.urlHttpGet(response.location, map, map2, i, httpCallback);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void urlHttpPost(String url, Map<String, String> paramsMap, String jsonData, Map<String, String> headerMap, int retryCount, HttpCallback callBack) {
        final String str = url;
        final Map<String, String> map = paramsMap;
        final String str2 = jsonData;
        final Map<String, String> map2 = headerMap;
        final HttpCallback httpCallback = callBack;
        final int i = retryCount;
        final int i2 = retryCount - 1;
        HttpTaskManager.execute(new Runnable() {
            public void run() {
                RealResponse response = new RealRequest().postData(str, RequestHelper.this.getPostBody(map, str2), RequestHelper.this.getPostBodyType(map, str2), map2);
                int i = response.code;
                if (i == 200 || i == 204) {
                    HttpCallback httpCallback = httpCallback;
                    if (httpCallback != null) {
                        httpCallback.onSuccess(response);
                    }
                } else if (RequestHelper.this.isRedirected || !HttpUtils.needRedirects(response.code)) {
                    int i2 = i2;
                    if (i2 != 0) {
                        RequestHelper.this.urlHttpPost(str, map, str2, map2, i2, httpCallback);
                        return;
                    }
                    HttpCallback httpCallback2 = httpCallback;
                    if (httpCallback2 != null) {
                        httpCallback2.onError(response);
                    }
                } else {
                    boolean unused = RequestHelper.this.isRedirected = true;
                    RequestHelper.this.urlHttpPost(response.location, map, str2, map2, i, httpCallback);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public String getUrl(String path, Map<String, String> paramsMap) {
        String path2;
        if (path == null || paramsMap == null) {
            return path;
        }
        if (!path.contains("?")) {
            path2 = path + "?";
        } else {
            path2 = path + "&";
        }
        for (String key : paramsMap.keySet()) {
            path2 = path2 + key + "=" + paramsMap.get(key) + "&";
        }
        return path2.substring(0, path2.length() - 1);
    }

    /* access modifiers changed from: private */
    public String getPostBody(Map<String, String> params, String jsonStr) {
        if (params != null) {
            return getPostBodyFormParamsMap(params);
        }
        if (!TextUtils.isEmpty(jsonStr)) {
            return jsonStr;
        }
        return null;
    }

    private String getPostBodyFormParamsMap(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public String getPostBodyType(Map<String, String> paramsMap, String jsonStr) {
        if (paramsMap == null && !TextUtils.isEmpty(jsonStr)) {
            return "application/json;charset=utf-8";
        }
        return null;
    }

    public static class Builder {
        private HttpCallback callBack;
        private Map<String, String> headerMap;
        private HttpMethod httpMethod;
        private String httpUrl;
        private String jsonData;
        private Map<String, String> paramsMap;
        private int retryCount = 1;

        public Builder(HttpMethod method, String url) {
            this.httpMethod = method;
            this.httpUrl = url;
        }

        public Builder params(Map<String, String> paramsMap2) {
            this.paramsMap = paramsMap2;
            return this;
        }

        public Builder jsonData(String data) {
            this.jsonData = data;
            return this;
        }

        public Builder header(Map<String, String> headerMap2) {
            this.headerMap = headerMap2;
            return this;
        }

        public Builder callback(HttpCallback callBack2) {
            this.callBack = callBack2;
            return this;
        }

        public Builder retryCount(int retryCount2) {
            this.retryCount = retryCount2;
            return this;
        }

        public void execute() {
            HttpMethod httpMethod2 = this.httpMethod;
            if (httpMethod2 == HttpMethod.POST && this.paramsMap == null) {
                new RequestHelper(this.httpUrl, this.jsonData, (Map) this.headerMap, this.retryCount, this.callBack);
            } else {
                new RequestHelper(httpMethod2, this.httpUrl, this.paramsMap, this.headerMap, this.retryCount, this.callBack);
            }
        }
    }
}
