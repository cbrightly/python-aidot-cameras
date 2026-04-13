package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.MessageApi;

public final /* synthetic */ class zzfa implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzfa();

    private zzfa() {
    }

    public final Object convert(Result result) {
        return Integer.valueOf(((MessageApi.SendMessageResult) result).getRequestId());
    }
}
