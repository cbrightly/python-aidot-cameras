package com.sensorsdata.analytics.android.sdk.visual.property;

import android.content.Context;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import androidx.work.WorkRequest;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.network.HttpMethod;
import com.sensorsdata.analytics.android.sdk.network.RequestHelper;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher;

public class VisualConfigRequestHelper {
    private static final String TAG = "SA.VP.VisualConfigRequestHelper";
    /* access modifiers changed from: private */
    public CountDownTimer mCountDownTimer;

    public interface IApiCallback {
        void onSuccess(String str);
    }

    public void requestVisualConfig(final Context context, final String version, final IApiCallback callback) {
        Dispatcher.getInstance().post(new Runnable() {
            public void run() {
                if (VisualConfigRequestHelper.this.mCountDownTimer != null) {
                    VisualConfigRequestHelper.this.mCountDownTimer.cancel();
                    CountDownTimer unused = VisualConfigRequestHelper.this.mCountDownTimer = null;
                }
                CountDownTimer unused2 = VisualConfigRequestHelper.this.mCountDownTimer = new CountDownTimer(90000, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS) {
                    public void onTick(long l) {
                        try {
                            if (TextUtils.isEmpty(SensorsDataAPI.sharedInstance().getServerUrl())) {
                                SALog.i(VisualConfigRequestHelper.TAG, "visualConfigRequest server url is null and return");
                                return;
                            }
                            AnonymousClass1 r1 = AnonymousClass1.this;
                            String requestUrl = VisualConfigRequestHelper.this.getRequestUrl(context, version);
                            if (TextUtils.isEmpty(requestUrl)) {
                                SALog.i(VisualConfigRequestHelper.TAG, "visualConfigRequest request url is null and return");
                            } else {
                                new RequestHelper.Builder(HttpMethod.GET, requestUrl).callback(new HttpCallback.StringCallback() {
                                    public void onFailure(int code, String errorMessage) {
                                        if (code == 304 || code == 404 || code == 205) {
                                            VisualConfigRequestHelper.this.resetTimer();
                                            if (code == 205) {
                                                VisualPropertiesManager.getInstance().save2Cache("");
                                            }
                                            SALog.i(VisualConfigRequestHelper.TAG, "requestVisualConfig return 304 Or 404");
                                        }
                                    }

                                    public void onResponse(String response) {
                                        VisualConfigRequestHelper.this.resetTimer();
                                        IApiCallback iApiCallback = callback;
                                        if (iApiCallback != null) {
                                            iApiCallback.onSuccess(response);
                                        }
                                        SALog.i(VisualConfigRequestHelper.TAG, "requestVisualConfig success response is " + response);
                                    }

                                    public void onAfter() {
                                    }
                                }).execute();
                            }
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                        }
                    }

                    public void onFinish() {
                    }
                };
                VisualConfigRequestHelper.this.mCountDownTimer.start();
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetTimer() {
        try {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        } catch (Throwable th) {
            this.mCountDownTimer = null;
            throw th;
        }
        this.mCountDownTimer = null;
    }

    /* access modifiers changed from: private */
    public String getRequestUrl(Context context, String version) {
        if (context == null) {
            SALog.i(TAG, "getRequestUrl context is null and return");
            return null;
        }
        String serverUrl = SensorsDataAPI.sharedInstance().getServerUrl();
        if (TextUtils.isEmpty(serverUrl)) {
            SALog.i(TAG, "visualConfigRequest server url is null and return");
            return null;
        }
        String baseUrl = null;
        int pathPrefix = serverUrl.lastIndexOf("/");
        if (pathPrefix != -1) {
            baseUrl = serverUrl.substring(0, pathPrefix) + "/config/visualized/Android.conf";
        }
        if (TextUtils.isEmpty(baseUrl)) {
            return null;
        }
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        if (!TextUtils.isEmpty(version)) {
            builder.appendQueryParameter("v", version);
        }
        String project = Uri.parse(serverUrl).getQueryParameter("project");
        if (!TextUtils.isEmpty(project)) {
            builder.appendQueryParameter("project", project);
        }
        String appId = AppInfoUtils.getProcessName(context);
        if (!TextUtils.isEmpty(appId)) {
            builder.appendQueryParameter("app_id", appId);
        }
        return builder.build().toString();
    }
}
