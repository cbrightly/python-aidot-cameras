package com.google.android.gms.internal.location;

import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public abstract class zzj extends zzb implements zzk {
    public zzj() {
        super("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzc.zzb(parcel);
                zzd((zzg) zzc.zza(parcel, zzg.CREATOR));
                return true;
            case 2:
                zze();
                return true;
            default:
                return false;
        }
    }
}
