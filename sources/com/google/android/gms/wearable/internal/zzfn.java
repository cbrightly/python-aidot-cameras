package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.NodeApi;

public final /* synthetic */ class zzfn implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzfn();

    private zzfn() {
    }

    public final Object convert(Result result) {
        return ((NodeApi.GetConnectedNodesResult) result).getNodes();
    }
}
