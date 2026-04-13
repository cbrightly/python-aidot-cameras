package com.sensorsdata.analytics.android.sdk.visual.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.network.HttpMethod;
import com.sensorsdata.analytics.android.sdk.network.RequestHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.HashMap;
import org.json.JSONObject;

public class PairingCodeRequestHelper {
    private static final String TAG = "SA.ParingCodeHttpRequest";
    private static final String URL_VERIFY_SUFFIX = "api/sdk/heat_maps/scanning/pairing_code";

    public interface IApiCallback {
        void onFailure(String str);

        void onSuccess();
    }

    public void verifyPairingCodeRequest(final Context context, String paringCode, final IApiCallback callback) {
        try {
            if (TextUtils.isEmpty(SensorsDataAPI.sharedInstance().getServerUrl())) {
                SALog.i(TAG, "verifyParingCodeRequest | server url is null and return");
                return;
            }
            Uri uri = Uri.parse(SensorsDataAPI.sharedInstance().getServerUrl());
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(uri.getScheme()).encodedAuthority(uri.getAuthority());
            HashMap<String, String> params = new HashMap<>();
            params.put("pairing_code", paringCode);
            HashMap<String, String> header = new HashMap<>();
            header.put("sensorsdata-project", uri.getQueryParameter("project"));
            new RequestHelper.Builder(HttpMethod.GET, builder.appendEncodedPath(URL_VERIFY_SUFFIX).toString()).params(params).header(header).callback(new HttpCallback.JsonCallback() {
                public void onFailure(int code, String errorMessage) {
                    IApiCallback iApiCallback = callback;
                    if (iApiCallback != null) {
                        iApiCallback.onFailure(errorMessage);
                    }
                }

                public void onResponse(JSONObject response) {
                    if (response != null) {
                        SALog.i(PairingCodeRequestHelper.TAG, "verifyParingCodeRequest onResponse | response: " + response.toString());
                        if (response.optBoolean("is_success")) {
                            String urlString = response.optString("url");
                            SALog.i(PairingCodeRequestHelper.TAG, "verifyParingCodeRequest onResponse | url: " + urlString);
                            if (!TextUtils.isEmpty(urlString)) {
                                SensorsDataUtils.handleSchemeUrl((Activity) context, new Intent().setData(Uri.parse(urlString)));
                            }
                            IApiCallback iApiCallback = callback;
                            if (iApiCallback != null) {
                                iApiCallback.onSuccess();
                                return;
                            }
                            return;
                        }
                        IApiCallback iApiCallback2 = callback;
                        if (iApiCallback2 != null) {
                            iApiCallback2.onFailure(response.optString("error_msg"));
                        }
                    }
                }

                public void onAfter() {
                }
            }).execute();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
