package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class SamsungImpl implements IRomOAID {
    private static final String TAG = "SA.SamsungImpl";
    private final Context mContext;
    private final OAIDService mService = new OAIDService();

    public SamsungImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        try {
            if (this.mContext.getPackageManager().getPackageInfo("com.samsung.android.deviceidservice", 0) != null) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return false;
        }
    }

    public String getRomOAID() {
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        try {
            if (!this.mContext.bindService(intent, this.mService, 1)) {
                return null;
            }
            String oaid = new SamsungInterface(OAIDService.BINDER_QUEUE.take()).getOAID();
            this.mContext.unbindService(this.mService);
            return oaid;
        } catch (Throwable throwable) {
            SALog.i(TAG, throwable);
            return null;
        }
    }

    public static class SamsungInterface implements IInterface {
        private final IBinder mIBinder;

        public SamsungInterface(IBinder iBinder) {
            this.mIBinder = iBinder;
        }

        public IBinder asBinder() {
            return this.mIBinder;
        }

        public String getOAID() {
            String str = null;
            try {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("com.samsung.android.deviceidservice.IDeviceIdService");
                this.mIBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
                obtain2.recycle();
                obtain.recycle();
                return str;
            } catch (Throwable th) {
                SALog.i(SamsungImpl.TAG, th);
                return str;
            }
        }
    }
}
