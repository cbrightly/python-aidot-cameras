package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class NubiaImpl implements IRomOAID {
    private static final String TAG = "SA.NubiaImpl";
    private final Context mContext;

    public NubiaImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public String getRomOAID() {
        if (!isSupported()) {
            SALog.i(TAG, "Only supports Android 10.0 and above for Nubia");
            return null;
        }
        String oaid = null;
        try {
            ContentProviderClient client = this.mContext.getContentResolver().acquireContentProviderClient(Uri.parse("content://cn.nubia.identity/identity"));
            if (client == null) {
                return null;
            }
            Bundle bundle = client.call("getOAID", (String) null, (Bundle) null);
            if (Build.VERSION.SDK_INT >= 24) {
                client.close();
            } else {
                client.release();
            }
            if (bundle == null) {
                SALog.i(TAG, "OAID query failed: bundle is null");
                return null;
            }
            if (bundle.getInt("code", -1) == 0) {
                oaid = bundle.getString("id");
            }
            SALog.i(TAG, "OAID query success: " + oaid);
            return oaid;
        } catch (Throwable th) {
            SALog.i(TAG, th);
        }
    }
}
