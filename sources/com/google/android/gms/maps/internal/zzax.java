package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzax extends zzb implements zzay {
    public zzax() {
        super("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        boolean zzb = zzb();
        parcel2.writeNoException();
        zzc.zzd(parcel2, zzb);
        return true;
    }
}
