package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzcs extends zzdu {
    final /* synthetic */ Boolean zza;
    final /* synthetic */ zzef zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcs(zzef zzef, Boolean bool) {
        super(zzef, true);
        this.zzb = zzef;
        this.zza = bool;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        if (this.zza != null) {
            ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).setMeasurementEnabled(this.zza.booleanValue(), this.zzh);
        } else {
            ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).clearMeasurementEnabled(this.zzh);
        }
    }
}
