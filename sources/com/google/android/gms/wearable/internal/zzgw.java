package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;

public final class zzgw extends zzgm<DataItemBuffer> {
    public zzgw(BaseImplementation.ResultHolder<DataItemBuffer> resultHolder) {
        super(resultHolder);
    }

    public final void zzb(DataHolder dataHolder) {
        zza(new DataItemBuffer(dataHolder));
    }
}
