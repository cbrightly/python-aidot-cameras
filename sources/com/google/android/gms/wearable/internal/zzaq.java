package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.wearable.Channel;

public final /* synthetic */ class zzaq implements PendingResultUtil.ResultConverter {
    static final PendingResultUtil.ResultConverter zzbx = new zzaq();

    private zzaq() {
    }

    public final Object convert(Result result) {
        return ((Channel.GetInputStreamResult) result).getInputStream();
    }
}
