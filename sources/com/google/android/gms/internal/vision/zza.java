package com.google.android.gms.internal.vision;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class zza extends Binder implements IInterface {
    private static zzc zza = null;

    protected zza(String str) {
        attachInterface(this, str);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        boolean z;
        if (i > 16777215) {
            z = super.onTransact(i, parcel, parcel2, i2);
        } else {
            parcel.enforceInterface(getInterfaceDescriptor());
            z = false;
        }
        if (z) {
            return true;
        }
        return zza(i, parcel, parcel2, i2);
    }

    /* access modifiers changed from: protected */
    public boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        return false;
    }
}
