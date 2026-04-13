package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.wearable.zzb;
import com.google.android.gms.internal.wearable.zzc;

public abstract class zzen extends zzb implements zzem {
    public zzen() {
        super("com.google.android.gms.wearable.internal.IWearableListener");
    }

    /* access modifiers changed from: protected */
    public final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zza((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                return true;
            case 2:
                zza((zzfe) zzc.zza(parcel, zzfe.CREATOR));
                return true;
            case 3:
                zza((zzfo) zzc.zza(parcel, zzfo.CREATOR));
                return true;
            case 4:
                zzb((zzfo) zzc.zza(parcel, zzfo.CREATOR));
                return true;
            case 5:
                onConnectedNodes(parcel.createTypedArrayList(zzfo.CREATOR));
                return true;
            case 6:
                zza((zzl) zzc.zza(parcel, zzl.CREATOR));
                return true;
            case 7:
                zza((zzaw) zzc.zza(parcel, zzaw.CREATOR));
                return true;
            case 8:
                zza((zzah) zzc.zza(parcel, zzah.CREATOR));
                return true;
            case 9:
                zza((zzi) zzc.zza(parcel, zzi.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
