package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.DataApi;

public final /* synthetic */ class zzcp implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzcp();

    private zzcp() {
    }

    public final Object convert(Result result) {
        return Integer.valueOf(((DataApi.DeleteDataItemsResult) result).getNumDeleted());
    }
}
