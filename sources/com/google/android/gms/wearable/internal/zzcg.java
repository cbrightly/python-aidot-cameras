package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;

public final class zzcg implements DataApi.DataItemResult {
    private final DataItem zzdg;
    private final Status zzp;

    public zzcg(Status status, DataItem dataItem) {
        this.zzp = status;
        this.zzdg = dataItem;
    }

    public final Status getStatus() {
        return this.zzp;
    }

    public final DataItem getDataItem() {
        return this.zzdg;
    }
}
