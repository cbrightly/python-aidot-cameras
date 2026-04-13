package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class XiaomiImpl implements IRomOAID {
    private static final String TAG = "SA.OAIDFactory";
    private final Context mContext;
    private Class<?> mIdProviderClass;
    private Object mIdProviderImpl;

    @SuppressLint({"PrivateApi"})
    public XiaomiImpl(Context context) {
        this.mContext = context;
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            this.mIdProviderClass = cls;
            this.mIdProviderImpl = cls.newInstance();
        } catch (Throwable th) {
            SALog.i(TAG, th);
        }
    }

    public boolean isSupported() {
        return this.mIdProviderImpl != null;
    }

    public String getRomOAID() {
        if (this.mIdProviderClass == null || this.mIdProviderImpl == null) {
            return null;
        }
        try {
            return getOAID();
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return null;
        }
    }

    private String getOAID() {
        return (String) this.mIdProviderClass.getMethod("getOAID", new Class[]{Context.class}).invoke(this.mIdProviderImpl, new Object[]{this.mContext});
    }
}
