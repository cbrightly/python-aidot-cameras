package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.internal.maps.zzq;
import com.google.android.gms.internal.maps.zzr;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzaa extends zzb implements zzab {
    public zzaa() {
        super("com.google.android.gms.maps.internal.IOnIndoorStateChangeListener");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzb();
                break;
            case 2:
                zzr zzb = zzq.zzb(parcel.readStrongBinder());
                zzc.zzc(parcel);
                zzc(zzb);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
