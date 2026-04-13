package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.measurement.zzbm;
import com.google.android.gms.internal.measurement.zzbo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzei extends zzbm implements zzek {
    zzei(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    public final String zzd(zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzq);
        Parcel zzb = zzb(11, zza);
        String readString = zzb.readString();
        zzb.recycle();
        return readString;
    }

    public final List zze(zzq zzq, boolean z) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzq);
        zza.writeInt(z ? 1 : 0);
        Parcel zzb = zzb(7, zza);
        ArrayList<zzlj> createTypedArrayList = zzb.createTypedArrayList(zzlj.CREATOR);
        zzb.recycle();
        return createTypedArrayList;
    }

    public final List zzf(String str, String str2, zzq zzq) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzbo.zzd(zza, zzq);
        Parcel zzb = zzb(16, zza);
        ArrayList<zzac> createTypedArrayList = zzb.createTypedArrayList(zzac.CREATOR);
        zzb.recycle();
        return createTypedArrayList;
    }

    public final List zzg(String str, String str2, String str3) {
        Parcel zza = zza();
        zza.writeString((String) null);
        zza.writeString(str2);
        zza.writeString(str3);
        Parcel zzb = zzb(17, zza);
        ArrayList<zzac> createTypedArrayList = zzb.createTypedArrayList(zzac.CREATOR);
        zzb.recycle();
        return createTypedArrayList;
    }

    public final List zzh(String str, String str2, boolean z, zzq zzq) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        int i = zzbo.zza;
        zza.writeInt(z ? 1 : 0);
        zzbo.zzd(zza, zzq);
        Parcel zzb = zzb(14, zza);
        ArrayList<zzlj> createTypedArrayList = zzb.createTypedArrayList(zzlj.CREATOR);
        zzb.recycle();
        return createTypedArrayList;
    }

    public final List zzi(String str, String str2, String str3, boolean z) {
        Parcel zza = zza();
        zza.writeString((String) null);
        zza.writeString(str2);
        zza.writeString(str3);
        int i = zzbo.zza;
        zza.writeInt(z ? 1 : 0);
        Parcel zzb = zzb(15, zza);
        ArrayList<zzlj> createTypedArrayList = zzb.createTypedArrayList(zzlj.CREATOR);
        zzb.recycle();
        return createTypedArrayList;
    }

    public final void zzj(zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzq);
        zzc(4, zza);
    }

    public final void zzk(zzaw zzaw, zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzaw);
        zzbo.zzd(zza, zzq);
        zzc(1, zza);
    }

    public final void zzl(zzaw zzaw, String str, String str2) {
        throw null;
    }

    public final void zzm(zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzq);
        zzc(18, zza);
    }

    public final void zzn(zzac zzac, zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzac);
        zzbo.zzd(zza, zzq);
        zzc(12, zza);
    }

    public final void zzo(zzac zzac) {
        throw null;
    }

    public final void zzp(zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzq);
        zzc(20, zza);
    }

    public final void zzq(long j, String str, String str2, String str3) {
        Parcel zza = zza();
        zza.writeLong(j);
        zza.writeString(str);
        zza.writeString(str2);
        zza.writeString(str3);
        zzc(10, zza);
    }

    public final void zzr(Bundle bundle, zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, bundle);
        zzbo.zzd(zza, zzq);
        zzc(19, zza);
    }

    public final void zzs(zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzq);
        zzc(6, zza);
    }

    public final void zzt(zzlj zzlj, zzq zzq) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzlj);
        zzbo.zzd(zza, zzq);
        zzc(2, zza);
    }

    public final byte[] zzu(zzaw zzaw, String str) {
        Parcel zza = zza();
        zzbo.zzd(zza, zzaw);
        zza.writeString(str);
        Parcel zzb = zzb(9, zza);
        byte[] createByteArray = zzb.createByteArray();
        zzb.recycle();
        return createByteArray;
    }
}
