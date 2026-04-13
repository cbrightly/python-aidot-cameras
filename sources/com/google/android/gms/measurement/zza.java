package com.google.android.gms.measurement;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzge;
import com.google.android.gms.measurement.internal.zzhe;
import com.google.android.gms.measurement.internal.zzhf;
import com.google.android.gms.measurement.internal.zzij;
import com.google.android.gms.measurement.internal.zzlj;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zza extends zzd {
    private final zzge zza;
    private final zzij zzb;

    public zza(@NonNull zzge zzge) {
        super((zzc) null);
        Preconditions.checkNotNull(zzge);
        this.zza = zzge;
        this.zzb = zzge.zzq();
    }

    public final int zza(String str) {
        this.zzb.zzh(str);
        return 25;
    }

    public final long zzb() {
        return this.zza.zzv().zzq();
    }

    public final Boolean zzc() {
        return this.zzb.zzi();
    }

    public final Double zzd() {
        return this.zzb.zzj();
    }

    public final Integer zze() {
        return this.zzb.zzl();
    }

    public final Long zzf() {
        return this.zzb.zzm();
    }

    public final String zzh() {
        return this.zzb.zzo();
    }

    public final String zzi() {
        return this.zzb.zzp();
    }

    public final String zzj() {
        return this.zzb.zzq();
    }

    public final String zzk() {
        return this.zzb.zzo();
    }

    public final String zzl() {
        return this.zzb.zzr();
    }

    public final List zzm(String str, String str2) {
        return this.zzb.zzs(str, str2);
    }

    public final Map zzn(boolean z) {
        List<zzlj> zzt = this.zzb.zzt(z);
        ArrayMap arrayMap = new ArrayMap(zzt.size());
        for (zzlj zzlj : zzt) {
            Object zza2 = zzlj.zza();
            if (zza2 != null) {
                arrayMap.put(zzlj.zzb, zza2);
            }
        }
        return arrayMap;
    }

    public final Map zzo(String str, String str2, boolean z) {
        return this.zzb.zzu(str, str2, z);
    }

    public final void zzp(String str) {
        this.zza.zzd().zzd(str, this.zza.zzax().elapsedRealtime());
    }

    public final void zzq(String str, String str2, Bundle bundle) {
        this.zza.zzq().zzA(str, str2, bundle);
    }

    public final void zzr(String str) {
        this.zza.zzd().zze(str, this.zza.zzax().elapsedRealtime());
    }

    public final void zzs(String str, String str2, Bundle bundle) {
        this.zzb.zzD(str, str2, bundle);
    }

    public final void zzt(String str, String str2, Bundle bundle, long j) {
        this.zzb.zzE(str, str2, bundle, true, false, j);
    }

    public final void zzu(zzhf zzhf) {
        this.zzb.zzJ(zzhf);
    }

    public final void zzv(Bundle bundle) {
        this.zzb.zzP(bundle);
    }

    public final void zzw(zzhe zzhe) {
        this.zzb.zzT(zzhe);
    }

    public final void zzx(zzhf zzhf) {
        this.zzb.zzZ(zzhf);
    }

    public final Object zzg(int i) {
        switch (i) {
            case 0:
                return this.zzb.zzr();
            case 1:
                return this.zzb.zzm();
            case 2:
                return this.zzb.zzj();
            case 3:
                return this.zzb.zzl();
            default:
                return this.zzb.zzi();
        }
    }
}
