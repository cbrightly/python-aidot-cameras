package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;
import java.security.MessageDigest;

public class OppoImpl implements IRomOAID {
    private static final String TAG = "SA.OppoImpl";
    private final Context mContext;
    private final OAIDService mService = new OAIDService();
    private String mSign;

    public OppoImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        try {
            if (this.mContext.getPackageManager().getPackageInfo("com.heytap.openid", 0) != null) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return false;
        }
    }

    public String getRomOAID() {
        Intent intent = new Intent("action.com.heytap.openid.OPEN_ID_SERVICE");
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        try {
            if (!this.mContext.bindService(intent, this.mService, 1)) {
                return null;
            }
            String oaid = realGetOUID();
            this.mContext.unbindService(this.mService);
            return oaid;
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return null;
        }
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    private String realGetOUID() {
        String pkgName = this.mContext.getPackageName();
        try {
            String str = this.mSign;
            if (str != null) {
                return getSerId(pkgName, str);
            }
            byte[] digest = MessageDigest.getInstance("SHA1").digest(this.mContext.getPackageManager().getPackageInfo(pkgName, 64).signatures[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
            }
            String sb2 = sb.toString();
            this.mSign = sb2;
            return getSerId(pkgName, sb2);
        } catch (Throwable throwable) {
            SALog.i(TAG, throwable);
            return null;
        }
    }

    private String getSerId(String pkgName, String sign) {
        return new OppoInterface(OAIDService.BINDER_QUEUE.take()).getSerID(pkgName, sign, "OUID");
    }

    public static class OppoInterface implements IInterface {
        private final IBinder mIBinder;

        OppoInterface(IBinder iBinder) {
            this.mIBinder = iBinder;
        }

        public IBinder asBinder() {
            return this.mIBinder;
        }

        public String getSerID(String packageName, String sign, String str) {
            String str4 = null;
            try {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                obtain.writeString(packageName);
                obtain.writeString(sign);
                obtain.writeString(str);
                this.mIBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                str4 = obtain2.readString();
                obtain.recycle();
                obtain2.recycle();
                return str4;
            } catch (Throwable th) {
                SALog.i(OppoImpl.TAG, th);
                return str4;
            }
        }
    }
}
