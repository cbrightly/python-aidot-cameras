package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import java.io.InputStream;

public final class zzcu extends DataClient.GetFdForAssetResponse implements Releasable {
    private final DataApi.GetFdForAssetResult zzdj;

    zzcu(DataApi.GetFdForAssetResult getFdForAssetResult) {
        this.zzdj = getFdForAssetResult;
    }

    public final ParcelFileDescriptor getFdForAsset() {
        return this.zzdj.getFd();
    }

    public final InputStream getInputStream() {
        return this.zzdj.getInputStream();
    }

    public final void release() {
        this.zzdj.release();
    }
}
