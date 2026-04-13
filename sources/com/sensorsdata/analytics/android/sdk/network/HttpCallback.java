package com.sensorsdata.analytics.android.sdk.network;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class HttpCallback<T> {
    static Handler sMainHandler = new Handler(Looper.getMainLooper());

    public abstract void onAfter();

    public abstract void onFailure(int i, String str);

    public abstract T onParseResponse(String str);

    public abstract void onResponse(T t);

    /* access modifiers changed from: package-private */
    public void onError(final RealResponse response) {
        final String errorMessage;
        if (!TextUtils.isEmpty(response.result)) {
            errorMessage = response.result;
        } else if (!TextUtils.isEmpty(response.errorMsg)) {
            errorMessage = response.errorMsg;
        } else {
            Exception exc = response.exception;
            if (exc != null) {
                errorMessage = exc.toString();
            } else {
                errorMessage = "unknown error";
            }
        }
        sMainHandler.post(new Runnable() {
            public void run() {
                HttpCallback.this.onFailure(response.code, errorMessage);
                HttpCallback.this.onAfter();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void onSuccess(RealResponse response) {
        final T obj = onParseResponse(response.result);
        sMainHandler.post(new Runnable() {
            public void run() {
                HttpCallback.this.onResponse(obj);
                HttpCallback.this.onAfter();
            }
        });
    }

    public static abstract class StringCallback extends HttpCallback<String> {
        public String onParseResponse(String result) {
            return result;
        }
    }

    public static abstract class JsonCallback extends HttpCallback<JSONObject> {
        public JSONObject onParseResponse(String result) {
            try {
                if (!TextUtils.isEmpty(result)) {
                    return new JSONObject(result);
                }
                return null;
            } catch (JSONException e) {
                SALog.printStackTrace(e);
                return null;
            }
        }

        public void onAfter() {
        }
    }
}
