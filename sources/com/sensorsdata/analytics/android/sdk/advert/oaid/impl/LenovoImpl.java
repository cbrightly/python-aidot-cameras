package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class LenovoImpl implements IRomOAID {
    private static final String TAG = "SA.LenovoImpl";
    private final Context mContext;
    private final OAIDService mService = new OAIDService();

    public LenovoImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        try {
            if (this.mContext.getPackageManager().getPackageInfo("com.zui.deviceidservice", 0) != null) {
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
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        try {
            if (!this.mContext.bindService(intent, this.mService, 1)) {
                return null;
            }
            String oaid = new LenovoInterface(OAIDService.BINDER_QUEUE.take()).getOAID();
            this.mContext.unbindService(this.mService);
            return oaid;
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return null;
        }
    }

    public static final class LenovoInterface implements IInterface {
        private final IBinder iBinder;

        private LenovoInterface(IBinder iBinder2) {
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
                obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                this.iBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
                obtain.recycle();
                obtain2.recycle();
                return str;
            } catch (Throwable th) {
                SALog.i(LenovoImpl.TAG, th);
                return str;
            }
        }
    }
}
