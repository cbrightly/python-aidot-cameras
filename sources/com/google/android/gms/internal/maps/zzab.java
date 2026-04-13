package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzab extends zza implements zzad {
    zzab(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IPolygonDelegate");
    }

    public final void zzA(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(13, zza);
    }

    public final boolean zzB(zzad zzad) {
        Parcel zza = zza();
        zzc.zzg(zza, zzad);
        Parcel zzH = zzH(19, zza);
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzC() {
        Parcel zzH = zzH(22, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzD() {
        Parcel zzH = zzH(18, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean zzE() {
        Parcel zzH = zzH(16, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final float zzd() {
        Parcel zzH = zzH(8, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final float zze() {
        Parcel zzH = zzH(14, zza());
        float readFloat = zzH.readFloat();
        zzH.recycle();
        return readFloat;
    }

    public final int zzf() {
        Parcel zzH = zzH(12, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final int zzg() {
        Parcel zzH = zzH(10, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final int zzh() {
        Parcel zzH = zzH(24, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final int zzi() {
        Parcel zzH = zzH(20, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    public final IObjectWrapper zzj() {
        Parcel zzH = zzH(28, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final String zzk() {
        Parcel zzH = zzH(2, zza());
        String readString = zzH.readString();
        zzH.recycle();
        return readString;
    }

    public final List zzl() {
        Parcel zzH = zzH(6, zza());
        ArrayList zzb = zzc.zzb(zzH);
        zzH.recycle();
        return zzb;
    }

    public final List zzm() {
        Parcel zzH = zzH(4, zza());
        ArrayList<LatLng> createTypedArrayList = zzH.createTypedArrayList(LatLng.CREATOR);
        zzH.recycle();
        return createTypedArrayList;
    }

    public final List zzn() {
        Parcel zzH = zzH(26, zza());
        ArrayList<PatternItem> createTypedArrayList = zzH.createTypedArrayList(PatternItem.CREATOR);
        zzH.recycle();
        return createTypedArrayList;
    }

    public final void zzo() {
        zzc(1, zza());
    }

    public final void zzp(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(21, zza);
    }

    public final void zzq(int i) {
        Parcel zza = zza();
        zza.writeInt(i);
        zzc(11, zza);
    }

    public final void zzr(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(17, zza);
    }

    public final void zzs(List list) {
        Parcel zza = zza();
        zza.writeList(list);
        zzc(5, zza);
    }

    public final void zzt(List list) {
        Parcel zza = zza();
        zza.writeTypedList(list);
        zzc(3, zza);
    }

    public final void zzu(int i) {
        Parcel zza = zza();
        zza.writeInt(i);
        zzc(9, zza);
    }

    public final void zzv(int i) {
        Parcel zza = zza();
        zza.writeInt(i);
        zzc(23, zza);
    }

    public final void zzw(List list) {
        Parcel zza = zza();
        zza.writeTypedList(list);
        zzc(25, zza);
    }

    public final void zzx(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zzc(7, zza);
    }

    public final void zzy(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        zzc(27, zza);
    }

    public final void zzz(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(15, zza);
    }
}
