package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzds extends zzdu {
    final /* synthetic */ Long zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ Bundle zzd;
    final /* synthetic */ boolean zze;
    final /* synthetic */ boolean zzf;
    final /* synthetic */ zzef zzg;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzds(zzef zzef, Long l, String str, String str2, Bundle bundle, boolean z, boolean z2) {
        super(zzef, true);
        this.zzg = zzef;
        this.zza = l;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = bundle;
        this.zze = z;
        this.zzf = z2;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        long j;
        Long l = this.zza;
        if (l == null) {
            j = this.zzh;
        } else {
            j = l.longValue();
        }
        ((zzcc) Preconditions.checkNotNull(this.zzg.zzj)).logEvent(this.zzb, this.zzc, this.zzd, this.zze, this.zzf, j);
    }
}
