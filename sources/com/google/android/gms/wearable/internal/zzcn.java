package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.DataItemBuffer;

public final /* synthetic */ class zzcn implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzcn();

    private zzcn() {
    }

    public final Object convert(Result result) {
        return (DataItemBuffer) result;
    }
}
