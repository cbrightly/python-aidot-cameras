package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;

public class zzgm<T> extends zza {
    private BaseImplementation.ResultHolder<T> zzet;

    public zzgm(BaseImplementation.ResultHolder<T> resultHolder) {
        this.zzet = resultHolder;
    }

    public final void zza(T t) {
        BaseImplementation.ResultHolder<T> resultHolder = this.zzet;
        if (resultHolder != null) {
            resultHolder.setResult(t);
            this.zzet = null;
        }
    }
}
