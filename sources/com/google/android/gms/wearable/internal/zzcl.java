package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.DataApi;

public final /* synthetic */ class zzcl implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzcl();

    private zzcl() {
    }

    public final Object convert(Result result) {
        return ((DataApi.DataItemResult) result).getDataItem();
    }
}
