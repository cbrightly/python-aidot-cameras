package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.NodeApi;

public final class zzgy extends zzgm<NodeApi.GetLocalNodeResult> {
    public zzgy(BaseImplementation.ResultHolder<NodeApi.GetLocalNodeResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzeg zzeg) {
        zza(new zzfk(zzgd.zzb(zzeg.statusCode), zzeg.zzea));
    }
}
