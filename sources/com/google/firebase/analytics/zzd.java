package com.google.firebase.analytics;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.measurement.zzef;
import com.google.android.gms.measurement.internal.zzhe;
import com.google.android.gms.measurement.internal.zzhf;
import com.google.android.gms.measurement.internal.zzik;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public final class zzd implements zzik {
    final /* synthetic */ zzef zza;

    zzd(zzef zzef) {
        this.zza = zzef;
    }

    public final int zza(String str) {
        return this.zza.zza(str);
    }

    public final long zzb() {
        return this.zza.zzb();
    }

    @Nullable
    public final Object zzg(int i) {
        return this.zza.zzi(i);
    }

    @Nullable
    public final String zzh() {
        return this.zza.zzm();
    }

    @Nullable
    public final String zzi() {
        return this.zza.zzn();
    }

    @Nullable
    public final String zzj() {
        return this.zza.zzo();
    }

    @Nullable
    public final String zzk() {
        return this.zza.zzp();
    }

    public final List zzm(@Nullable String str, @Nullable String str2) {
        return this.zza.zzq(str, str2);
    }

    public final Map zzo(@Nullable String str, @Nullable String str2, boolean z) {
        return this.zza.zzr(str, str2, z);
    }

    public final void zzp(String str) {
        this.zza.zzv(str);
    }

    public final void zzq(String str, @Nullable String str2, @Nullable Bundle bundle) {
        this.zza.zzw(str, str2, bundle);
    }

    public final void zzr(String str) {
        this.zza.zzx(str);
    }

    public final void zzs(String str, String str2, Bundle bundle) {
        this.zza.zzz(str, str2, bundle);
    }

    public final void zzt(String str, String str2, Bundle bundle, long j) {
        this.zza.zzA(str, str2, bundle, j);
    }

    public final void zzu(zzhf zzhf) {
        this.zza.zzC(zzhf);
    }

    public final void zzv(Bundle bundle) {
        this.zza.zzE(bundle);
    }

    public final void zzw(zzhe zzhe) {
        this.zza.zzK(zzhe);
    }

    public final void zzx(zzhf zzhf) {
        this.zza.zzP(zzhf);
    }
}
