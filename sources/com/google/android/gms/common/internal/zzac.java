package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzc;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class zzac extends zzb implements IGmsCallbacks {
    public zzac() {
        super("com.google.android.gms.common.internal.IGmsCallbacks");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzc.zzb(parcel);
                onPostInitComplete(parcel.readInt(), parcel.readStrongBinder(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 2:
                zzc.zzb(parcel);
                zzb(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 3:
                zzc.zzb(parcel);
                zzc(parcel.readInt(), parcel.readStrongBinder(), (zzk) zzc.zza(parcel, zzk.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
