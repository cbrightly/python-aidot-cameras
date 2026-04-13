package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.CapabilityApi;

public final /* synthetic */ class zzab implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzab();

    private zzab() {
    }

    public final Object convert(Result result) {
        return ((CapabilityApi.GetCapabilityResult) result).getCapability();
    }
}
