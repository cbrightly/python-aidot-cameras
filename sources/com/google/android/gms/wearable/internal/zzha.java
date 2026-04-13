package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.ChannelApi;

public final class zzha extends zzgm<ChannelApi.OpenChannelResult> {
    public zzha(BaseImplementation.ResultHolder<ChannelApi.OpenChannelResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzfq zzfq) {
        zza(new zzam(zzgd.zzb(zzfq.statusCode), zzfq.zzck));
    }
}
