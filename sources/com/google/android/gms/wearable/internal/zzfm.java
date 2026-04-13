package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.NodeApi;

public final /* synthetic */ class zzfm implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzfm();

    private zzfm() {
    }

    public final Object convert(Result result) {
        return ((NodeApi.GetLocalNodeResult) result).getNode();
    }
}
