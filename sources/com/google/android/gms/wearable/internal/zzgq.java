package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzgq extends zzgm<CapabilityApi.GetAllCapabilitiesResult> {
    public zzgq(BaseImplementation.ResultHolder<CapabilityApi.GetAllCapabilitiesResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzdi zzdi) {
        zza(new zzx(zzgd.zzb(zzdi.statusCode), zzgk.zza(zzdi.zzdp)));
    }
}
