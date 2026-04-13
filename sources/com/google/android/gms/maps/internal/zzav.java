package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.internal.maps.zzz;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzav extends zzb implements zzaw {
    public zzav() {
        super("com.google.android.gms.maps.internal.IOnMarkerDragListener");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzaa zzb = zzz.zzb(parcel.readStrongBinder());
                zzc.zzc(parcel);
                zzd(zzb);
                break;
            case 2:
                zzaa zzb2 = zzz.zzb(parcel.readStrongBinder());
                zzc.zzc(parcel);
                zzb(zzb2);
                break;
            case 3:
                zzaa zzb3 = zzz.zzb(parcel.readStrongBinder());
                zzc.zzc(parcel);
                zzc(zzb3);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
