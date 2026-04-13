package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzp extends zza implements zzr {
    zzp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
    }

    public final int zzd() {
        Parcel zzH = zzH(1, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final int zze() {
        Parcel zzH = zzH(2, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final int zzf() {
        Parcel zzH = zzH(6, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final List zzg() {
        Parcel zzH = zzH(3, zza());
        ArrayList<IBinder> createBinderArrayList = zzH.createBinderArrayList();
        zzH.recycle();
        return createBinderArrayList;
    }

    public final boolean zzh(zzr zzr) {
        Parcel zza = zza();
        zzc.zzg(zza, zzr);
        Parcel zzH = zzH(5, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzi() {
        Parcel zzH = zzH(4, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }
}
