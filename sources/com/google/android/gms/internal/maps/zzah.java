package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzah extends zza implements zzaj {
    zzah(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
    }

    public final float zzd() {
        Parcel zzH = zzH(13, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zze() {
        Parcel zzH = zzH(5, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final int zzf() {
        Parcel zzH = zzH(9, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final String zzg() {
        Parcel zzH = zzH(3, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final void zzh() {
        zzc(2, zza());
    }

    public final void zzi() {
        zzc(1, zza());
    }

    public final void zzj(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(10, zza);
    }

    public final void zzk(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(12, zza);
    }

    public final void zzl(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(6, zza);
    }

    public final void zzm(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(4, zza);
    }

    public final boolean zzn(zzaj zzaj) {
        Parcel zza = zza();
        zzc.zzg(zza, zzaj);
        Parcel zzH = zzH(8, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzo() {
        Parcel zzH = zzH(11, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzp() {
        Parcel zzH = zzH(7, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }
}
