package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzgr extends zzgm<CapabilityApi.GetCapabilityResult> {
    public zzgr(BaseImplementation.ResultHolder<CapabilityApi.GetCapabilityResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzdk zzdk) {
        Status zzb = zzgd.zzb(zzdk.statusCode);
        zzah zzah = zzdk.zzdq;
        zza(new zzy(zzb, zzah == null ? null : new zzw(zzah)));
    }
}
