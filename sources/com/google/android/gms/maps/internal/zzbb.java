package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Parcel;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzbb extends zzb implements zzbc {
    public zzbb() {
        super("com.google.android.gms.maps.internal.IOnMyLocationClickListener");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        zzc.zzc(parcel);
        zzb((Location) zzc.zza(parcel, Location.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
