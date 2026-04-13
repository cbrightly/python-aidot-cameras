package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzm extends zza implements zzo {
    zzm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
    }

    public final boolean zzA() {
        Parcel zzH = zzH(23, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzB() {
        Parcel zzH = zzH(16, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final float zzd() {
        Parcel zzH = zzH(12, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zze() {
        Parcel zzH = zzH(8, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zzf() {
        Parcel zzH = zzH(18, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zzg() {
        Parcel zzH = zzH(7, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zzh() {
        Parcel zzH = zzH(14, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final int zzi() {
        Parcel zzH = zzH(20, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final IObjectWrapper zzj() {
        Parcel zzH = zzH(25, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final LatLng zzk() {
        Parcel zzH = zzH(4, zza());
        LatLng latLng = (LatLng) zzc.zza(zzH, LatLng.CREATOR);
        zzH.recycle();
        return latLng;
    }

    public final LatLngBounds zzl() {
        Parcel zzH = zzH(10, zza());
        LatLngBounds latLngBounds = (LatLngBounds) zzc.zza(zzH, LatLngBounds.CREATOR);
        zzH.recycle();
        return latLngBounds;
    }

    public final String zzm() {
        Parcel zzH = zzH(2, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final void zzn() {
        zzc(1, zza());
    }

    public final void zzo(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(11, zza);
    }

    public final void zzp(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(22, zza);
    }

    public final void zzq(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(5, zza);
    }

    public final void zzr(float f, float f2) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zza.writeFloat(f2);
        zzc(6, zza);
    }

    public final void zzs(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(21, zza);
    }

    public final void zzt(LatLng latLng) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zzc(3, zza);
    }

    public final void zzu(LatLngBounds latLngBounds) {
        Parcel zza = zza();
        zzc.zze(zza, latLngBounds);
        zzc(9, zza);
    }

    public final void zzv(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(24, zza);
    }

    public final void zzw(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(17, zza);
    }

    public final void zzx(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(15, zza);
    }

    public final void zzy(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(13, zza);
    }

    public final boolean zzz(zzo zzo) {
        Parcel zza = zza();
        zzc.zzg(zza, zzo);
        Parcel zzH = zzH(19, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }
}
