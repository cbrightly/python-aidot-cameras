package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class CoolpadImpl implements IRomOAID {
    private static final String TAG = "SA.CoolpadImpl";
    private final Context context;
    private final OAIDService service = new OAIDService();

    public CoolpadImpl(Context context2) {
        this.context = context2;
    }

    public boolean isSupported() {
        try {
            if (this.context.getPackageManager().getPackageInfo("com.coolpad.deviceidsupport", 0) != null) {
                return true;
            }
            return false;
        } catch (Throwable throwable) {
            SALog.i(TAG, throwable);
            return false;
        }
    }

    public String getRomOAID() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.coolpad.deviceidsupport", "com.coolpad.deviceidsupport.DeviceIdService"));
        try {
            if (!this.context.bindService(intent, this.service, 1)) {
                return null;
            }
            String oaid = new CoolpadInterface(OAIDService.BINDER_QUEUE.take()).getOAID(this.context.getPackageName());
            this.context.unbindService(this.service);
            return oaid;
        } catch (Throwable throwable) {
            SALog.i(TAG, throwable);
            return null;
        }
    }

    public static class CoolpadInterface implements IInterface {
        private final IBinder mIBinder;

        CoolpadInterface(IBinder iBinder) {
            this.mIBinder = iBinder;
        }

        public IBinder asBinder() {
            return this.mIBinder;
        }

        public String getOAID(String packageName) {
            String str = null;
            try {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                obtain.writeInterfaceToken("com.coolpad.deviceidsupport.IDeviceIdManager");
                obtain.writeString(packageName);
                this.mIBinder.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
                obtain.recycle();
                obtain2.recycle();
                return str;
            } catch (Throwable th) {
                SALog.i(CoolpadImpl.TAG, th);
                return str;
            }
        }
    }
}
