package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.internal.maps.zzz;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzh extends zzb implements zzi {
    public zzh() {
        super("com.google.android.gms.maps.internal.IInfoWindowAdapter");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzaa zzb = zzz.zzb(parcel.readStrongBinder());
                zzc.zzc(parcel);
                IObjectWrapper zzc = zzc(zzb);
                parcel2.writeNoException();
                zzc.zzg(parcel2, zzc);
                return true;
            case 2:
                zzaa zzb2 = zzz.zzb(parcel.readStrongBinder());
                zzc.zzc(parcel);
                IObjectWrapper zzb3 = zzb(zzb2);
                parcel2.writeNoException();
                zzc.zzg(parcel2, zzb3);
                return true;
            default:
                return false;
        }
    }
}
