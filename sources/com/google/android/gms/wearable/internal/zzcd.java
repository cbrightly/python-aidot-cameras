package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItemAsset;

public final class zzcd extends zzn<DataApi.GetFdForAssetResult> {
    private final /* synthetic */ DataItemAsset zzde;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcd(zzbw zzbw, GoogleApiClient googleApiClient, DataItemAsset dataItemAsset) {
        super(googleApiClient);
        this.zzde = dataItemAsset;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<DataApi.GetFdForAssetResult>) this, Asset.createFromRef(this.zzde.getId()));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzci(status, (ParcelFileDescriptor) null);
    }
}
