package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzy extends zza implements zzaa {
    zzy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IMarkerDelegate");
    }

    public final void zzA(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(27, zza);
    }

    public final void zzB() {
        zzc(11, zza());
    }

    public final boolean zzC(zzaa zzaa) {
        Parcel zza = zza();
        zzc.zzg(zza, zzaa);
        Parcel zzH = zzH(16, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzD() {
        Parcel zzH = zzH(10, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzE() {
        Parcel zzH = zzH(21, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzF() {
        Parcel zzH = zzH(13, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzG() {
        Parcel zzH = zzH(15, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final float zzd() {
        Parcel zzH = zzH(26, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zze() {
        Parcel zzH = zzH(23, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zzf() {
        Parcel zzH = zzH(28, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final int zzg() {
        Parcel zzH = zzH(17, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final IObjectWrapper zzh() {
        Parcel zzH = zzH(30, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final LatLng zzi() {
        Parcel zzH = zzH(4, zza());
        LatLng latLng = (LatLng) zzc.zza(zzH, LatLng.CREATOR);
        zzH.recycle();
        return latLng;
    }

    public final String zzj() {
        Parcel zzH = zzH(2, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final String zzk() {
        Parcel zzH = zzH(8, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final String zzl() {
        Parcel zzH = zzH(6, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final void zzm() {
        zzc(12, zza());
    }

    public final void zzn() {
        zzc(1, zza());
    }

    public final void zzo(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(25, zza);
    }

    public final void zzp(float f, float f2) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zza.writeFloat(f2);
        zzc(19, zza);
    }

    public final void zzq(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(9, zza);
    }

    public final void zzr(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(20, zza);
    }

    public final void zzs(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(18, zza);
    }

    public final void zzt(float f, float f2) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zza.writeFloat(f2);
        zzc(24, zza);
    }

    public final void zzu(LatLng latLng) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zzc(3, zza);
    }

    public final void zzv(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(22, zza);
    }

    public final void zzw(String str) {
        Parcel zza = zza();
        zza.writeString(str);
        zzc(7, zza);
    }

    public final void zzx(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(29, zza);
    }

    public final void zzy(String str) {
        Parcel zza = zza();
        zza.writeString(str);
        zzc(5, zza);
    }

    public final void zzz(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(14, zza);
    }
}
