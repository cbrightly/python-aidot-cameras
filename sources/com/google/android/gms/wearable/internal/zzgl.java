package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzgl extends zzgm<CapabilityApi.AddLocalCapabilityResult> {
    public zzgl(BaseImplementation.ResultHolder<CapabilityApi.AddLocalCapabilityResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzf zzf) {
        zza(new zzu(zzgd.zzb(zzf.statusCode)));
    }
}
