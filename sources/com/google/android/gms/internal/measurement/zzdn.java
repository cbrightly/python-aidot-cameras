package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzdn extends zzdu {
    final /* synthetic */ boolean zza;
    final /* synthetic */ zzef zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdn(zzef zzef, boolean z) {
        super(zzef, true);
        this.zzb = zzef;
        this.zza = z;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).setDataCollectionEnabled(this.zza);
    }
}
