package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzs extends zza implements zzu {
    zzs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
    }

    public final int zzd() {
        Parcel zzH = zzH(5, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final String zze() {
        Parcel zzH = zzH(1, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final String zzf() {
        Parcel zzH = zzH(2, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final void zzg() {
        zzc(3, zza());
    }

    public final boolean zzh(zzu zzu) {
        Parcel zza = zza();
        zzc.zzg(zza, zzu);
        Parcel zzH = zzH(4, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }
}
