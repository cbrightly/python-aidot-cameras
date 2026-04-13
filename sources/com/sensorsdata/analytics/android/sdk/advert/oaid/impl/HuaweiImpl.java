package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.provider.Settings;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class HuaweiImpl implements IRomOAID {
    private static final String TAG = "SA.HuaweiImpl";
    private final Context mContext;
    private String mPackageName;
    private final OAIDService mService = new OAIDService();

    public HuaweiImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        try {
            PackageManager pm = this.mContext.getPackageManager();
            boolean ret = false;
            if (pm.getPackageInfo("com.huawei.hwid", 0) != null) {
                this.mPackageName = "com.huawei.hwid";
                return true;
            } else if (pm.getPackageInfo("com.huawei.hwid.tv", 0) != null) {
                this.mPackageName = "com.huawei.hwid.tv";
                return true;
            } else {
                this.mPackageName = "com.huawei.hms";
                if (pm.getPackageInfo("com.huawei.hms", 0) != null) {
                    ret = true;
                }
                return ret;
            }
        } catch (Throwable t) {
            SALog.i(TAG, t);
            return false;
        }
    }

    public String getRomOAID() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                String oaid = Settings.Global.getString(this.mContext.getContentResolver(), "pps_oaid");
                if (!TextUtils.isEmpty(oaid)) {
                    SALog.i(TAG, "Get oaid from global settings");
                    return oaid;
                }
            } catch (Throwable t) {
                SALog.i(TAG, t);
            }
        }
        if (!TextUtils.isEmpty(this.mPackageName) || isSupported()) {
            Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
            intent.setPackage(this.mPackageName);
            try {
                if (this.mContext.bindService(intent, this.mService, 1)) {
                    return new HuaWeiInterface(OAIDService.BINDER_QUEUE.take()).getOAID();
                }
                return null;
            } catch (Throwable t2) {
                SALog.i(TAG, t2);
                return null;
            }
        } else {
            SALog.i(TAG, "Huawei Advertising ID not available");
            return null;
        }
    }

    public static final class HuaWeiInterface implements IInterface {
        private final IBinder iBinder;

        private HuaWeiInterface(IBinder iBinder2) {
            this.iBinder = iBinder2;
        }

        public IBinder asBinder() {
            return this.iBinder;
        }

        public String getOAID() {
            String str = null;
            try {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
                this.iBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
                obtain.recycle();
                obtain2.recycle();
                return str;
            } catch (Throwable th) {
                SALog.i(HuaweiImpl.TAG, th);
                return str;
            }
        }
    }
}
