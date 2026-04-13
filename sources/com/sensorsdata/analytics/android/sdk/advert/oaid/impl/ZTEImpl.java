package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;
import java.lang.reflect.Constructor;

public class ZTEImpl implements IRomOAID {
    private static final String ID_PACKAGE = "com.mdid.msa";
    private static final String TAG = "SA.ZTEImpl";
    private static final String ZTE_MANAGER = "android.app.ZteDeviceIdentifyManager";
    private final Context mContext;
    private final OAIDService mService = new OAIDService();

    ZTEImpl(Context context) {
        this.mContext = context;
    }

    @SuppressLint({"PrivateApi"})
    private static String getOAID30(Context context) {
        try {
            Class<?> cls = Class.forName(ZTE_MANAGER);
            Constructor<?> declaredConstructor = cls.getDeclaredConstructor(new Class[]{Context.class});
            Constructor<?> declaredConstructor2 = declaredConstructor;
            if (declaredConstructor == null) {
                return null;
            }
            Object newInstance = declaredConstructor2.newInstance(new Object[]{context});
            return (String) cls.getDeclaredMethod("getOAID", new Class[]{Context.class}).invoke(newInstance, new Object[]{context});
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return null;
        }
    }

    private static void startMsaklServer(String str, Context context) {
        Intent intent = new Intent();
        intent.setClassName(ID_PACKAGE, "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        try {
            intent.putExtra("com.bun.msa.param.runinset", true);
            context.startService(intent);
        } catch (Throwable th) {
            SALog.i(TAG, th);
        }
    }

    public boolean isSupported() {
        try {
            if (Build.VERSION.SDK_INT > 29) {
                return true;
            }
            this.mContext.getPackageManager().getPackageInfo(ID_PACKAGE, 0);
            return true;
        } catch (Throwable t) {
            SALog.i(TAG, t);
            return false;
        }
    }

    public String getRomOAID() {
        return bindZTEServiceGetOAID(this.mContext);
    }

    public String bindZTEServiceGetOAID(Context context) {
        if (Build.VERSION.SDK_INT <= 29) {
            return getOAID29(context);
        }
        return getOAID30(context);
    }

    private String getOAID29(Context context) {
        String oaid = null;
        try {
            String packageName = context.getPackageName();
            startMsaklServer(packageName, context);
            Intent intent = new Intent();
            intent.setClassName(ID_PACKAGE, "com.mdid.msa.service.MsaIdService");
            intent.setAction("com.bun.msa.action.bindto.service");
            intent.putExtra("com.bun.msa.param.pkgname", packageName);
            if (!context.bindService(intent, this.mService, 1)) {
                return null;
            }
            oaid = new ZTEInterface(OAIDService.BINDER_QUEUE.take()).getOAID();
            context.unbindService(this.mService);
            return oaid;
        } catch (Throwable t) {
            SALog.i(TAG, t);
        }
    }

    public static class ZTEInterface implements IInterface {
        private final IBinder mIBinder;

        ZTEInterface(IBinder iBinder) {
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
                obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                this.mIBinder.transact(3, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
                obtain.recycle();
                obtain2.recycle();
                return str;
            } catch (Throwable th) {
                SALog.i(ZTEImpl.TAG, th);
                return str;
            }
        }
    }
}
