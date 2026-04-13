package com.google.android.gms.maps.internal;

import android.graphics.Bitmap;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzbu extends zzb implements zzbv {
    public zzbu() {
        super("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzc.zzc(parcel);
                zzb((Bitmap) zzc.zza(parcel, Bitmap.CREATOR));
                break;
            case 2:
                IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                zzc.zzc(parcel);
                zzc(asInterface);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
