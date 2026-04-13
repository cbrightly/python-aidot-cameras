package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzdr extends zzdu {
    final /* synthetic */ zzdw zza;
    final /* synthetic */ zzef zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdr(zzef zzef, zzdw zzdw) {
        super(zzef, true);
        this.zzb = zzef;
        this.zza = zzdw;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).unregisterOnMeasurementEventListener(this.zza);
    }
}
