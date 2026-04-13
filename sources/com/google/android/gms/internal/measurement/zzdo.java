package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzdo extends zzdu {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zzef zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdo(zzef zzef, Bundle bundle) {
        super(zzef, true);
        this.zzb = zzef;
        this.zza = bundle;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).setDefaultEventParameters(this.zza);
    }
}
