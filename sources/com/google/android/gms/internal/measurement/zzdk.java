package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzdk extends zzdu {
    final /* synthetic */ zzbz zza;
    final /* synthetic */ zzef zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdk(zzef zzef, zzbz zzbz) {
        super(zzef, true);
        this.zzb = zzef;
        this.zza = zzbz;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).getAppInstanceId(this.zza);
    }

    /* access modifiers changed from: protected */
    public final void zzb() {
        this.zza.zze((Bundle) null);
    }
}
