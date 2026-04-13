package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.maps.model.PointOfInterest;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzbd extends zzb implements zzbe {
    public zzbd() {
        super("com.google.android.gms.maps.internal.IOnPoiClickListener");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        zzc.zzc(parcel);
        zzb((PointOfInterest) zzc.zza(parcel, PointOfInterest.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
