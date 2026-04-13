package com.google.android.libraries.places.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjp extends zzjq {
    final transient int zza;
    final transient int zzb;
    final /* synthetic */ zzjq zzc;

    zzjp(zzjq zzjq, int i, int i2) {
        this.zzc = zzjq;
        this.zza = i;
        this.zzb = i2;
    }

    public final Object get(int i) {
        zziy.zza(i, this.zzb, FirebaseAnalytics.Param.INDEX);
        return this.zzc.get(i + this.zza);
    }

    public final int size() {
        return this.zzb;
    }

    public final /* bridge */ /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }

    /* access modifiers changed from: package-private */
    public final int zzb() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        return true;
    }

    /* access modifiers changed from: package-private */
    @CheckForNull
    public final Object[] zzg() {
        return this.zzc.zzg();
    }

    public final zzjq zzh(int i, int i2) {
        zziy.zzi(i, i2, this.zzb);
        zzjq zzjq = this.zzc;
        int i3 = this.zza;
        return zzjq.subList(i + i3, i2 + i3);
    }
}
