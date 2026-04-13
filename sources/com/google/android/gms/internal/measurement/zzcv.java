package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzcv extends zzdu {
    final /* synthetic */ zzef zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcv(zzef zzef) {
        super(zzef, true);
        this.zza = zzef;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        ((zzcc) Preconditions.checkNotNull(this.zza.zzj)).resetAnalyticsData(this.zzh);
    }
}
